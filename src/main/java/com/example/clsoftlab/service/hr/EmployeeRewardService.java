package com.example.clsoftlab.service.hr;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.EmployeeRewardDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeRewardRequestDto;
import com.example.clsoftlab.dto.hr.EmployeeRewardSimpleDto;
import com.example.clsoftlab.entity.EmployeeReward;
import com.example.clsoftlab.repository.hr.EmployeeRewardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeRewardService {

	private final EmployeeRewardRepository employeeRewardRepository;
	private final ModelMapper modelMapper;
	
	// 사번으로 포상 page 조회
	public Page<EmployeeRewardDetailDto> findByPernr (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		return employeeRewardRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EmployeeRewardDetailDto.class));
	}
	
	// 사번으로 simple List 조회
	public List<EmployeeRewardSimpleDto> getSimpleList (String pernr) {
		return employeeRewardRepository.findAllByPernrOrderBySeq(pernr).stream()
				.map(i -> modelMapper.map(i, EmployeeRewardSimpleDto.class))
				.toList();
	}
	
	// 포상 목록 저장
	@Transactional
	public void saveRewardList (String pernr, List<EmployeeRewardRequestDto> dtoList) {
		
		Set<Integer> rewardSeqs = new HashSet<>();
		
		List<EmployeeReward> originalReward = employeeRewardRepository.findAllByPernrOrderBySeq(pernr);
		
		// 순차 검사
		for (EmployeeRewardRequestDto dto : dtoList) {
			if (!rewardSeqs.add(dto.getSeq())) {
				throw new IllegalArgumentException("순번 '" + dto.getSeq() + "'이(가) 중복으로 입력되었습니다.");
			}
		}
		
		Map<Long, EmployeeReward> originalMap = originalReward.stream()
				.collect(Collectors.toMap(EmployeeReward::getId, reward -> reward));
		
		Map<Long, EmployeeRewardRequestDto> dtoMap = dtoList.stream()
				.filter(dto -> dto.getId() != null)
				.collect(Collectors.toMap(EmployeeRewardRequestDto::getId, dto -> dto));
		
		try {
			// 원본에서 없어진 삭제 대상
			List<EmployeeReward> rewardToDelete = originalReward.stream()
					.filter(reward -> !dtoMap.containsKey(reward.getId()))
					.toList();
			
			if (!rewardToDelete.isEmpty()) {
				employeeRewardRepository.deleteAllInBatch(rewardToDelete);
			}
			
			for (EmployeeRewardRequestDto dto : dtoList) {
				// Id가 없으면 신규 등록
				if (dto.getId() == null) {
					employeeRewardRepository.save(modelMapper.map(dto, EmployeeReward.class));
				} else { // Id가 있으면 수정
					EmployeeReward employeeReward = originalMap.get(dto.getId());
					if (employeeReward != null) {
						employeeReward.update(dto);
						employeeRewardRepository.saveAndFlush(employeeReward);
					}
					
				}
			}
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("순번이 중복되었습니다.");
		}
	}
}
