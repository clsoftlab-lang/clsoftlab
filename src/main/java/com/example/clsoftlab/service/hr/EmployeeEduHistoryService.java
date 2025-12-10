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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.EmployeeEduHistoryDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeEduHistoryRequestDto;
import com.example.clsoftlab.dto.hr.EmployeeEduHistorySimpleDto;
import com.example.clsoftlab.entity.EmployeeEduHistory;
import com.example.clsoftlab.repository.hr.EmployeeEduHistoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeEduHistoryService {

	private final EmployeeEduHistoryRepository employeeEduHistoryRepository;
	private final ModelMapper modelMapper;
	
	// 사번으로 page 조회
	public Page<EmployeeEduHistoryDetailDto> findByPernr (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("seq").ascending());
		
		return employeeEduHistoryRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EmployeeEduHistoryDetailDto.class));
	}
	
	// 사번으로 simpleList 조회
	public List<EmployeeEduHistorySimpleDto> getSimpleList (String pernr) {
		return employeeEduHistoryRepository.findAllByPernrOrderBySeq(pernr).stream()
				.map(i -> modelMapper.map(i, EmployeeEduHistorySimpleDto.class))
				.toList();
	}
	
	// 학력 리스트 저장
	@Transactional
	public void saveHistoryList (String pernr, List<EmployeeEduHistoryRequestDto> dtoList) {
		Set<Integer> historySeqs = new HashSet<>();
		
		for (EmployeeEduHistoryRequestDto dto : dtoList) {
			if(!historySeqs.add(dto.getSeq())) {
				throw new IllegalArgumentException("순번 '" + dto.getSeq() + "'이(가) 중복으로 입력되었습니다.");
			}
		}
		
		List<EmployeeEduHistory> originalList = employeeEduHistoryRepository.findAllByPernrOrderBySeq(pernr);
		
		Map<Long, EmployeeEduHistory> originalMap = originalList.stream()
				.collect(Collectors.toMap(EmployeeEduHistory::getId, history -> history));
		
		Map<Long, EmployeeEduHistoryRequestDto> dtoMap = dtoList.stream()
				.filter(dto -> dto.getId()!=null)
				.collect(Collectors.toMap(EmployeeEduHistoryRequestDto::getId, dto -> dto));
		
		try {
			List<EmployeeEduHistory> historyToDelete = originalList.stream() // 삭제대상
					.filter(history -> !dtoMap.containsKey(history.getId()))
					.toList();
			
			if (!historyToDelete.isEmpty()) {
				employeeEduHistoryRepository.deleteAllInBatch(historyToDelete);
			}
			
			for (EmployeeEduHistoryRequestDto dto : dtoList) {
				if (dto.getId() == null) { //신규 등록
					employeeEduHistoryRepository.save(modelMapper.map(dto, EmployeeEduHistory.class));
				} else { // 수정
					EmployeeEduHistory employeeEduHistory = originalMap.get(dto.getId());
					if (employeeEduHistory != null) {
						employeeEduHistory.update(dto);
						employeeEduHistoryRepository.saveAndFlush(employeeEduHistory);
					}
				}
			}
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("순번이 중복되었습니다.");
		}
	}
}
