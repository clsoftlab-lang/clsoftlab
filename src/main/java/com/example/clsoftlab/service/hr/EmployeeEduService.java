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

import com.example.clsoftlab.dto.hr.EmployeeEduDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeEduRequestDto;
import com.example.clsoftlab.entity.EmployeeEdu;
import com.example.clsoftlab.repository.hr.EmployeeEduRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeEduService {

	private final EmployeeEduRepository employeeEduRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeEduService(EmployeeEduRepository employeeEduRepository, ModelMapper modelMapper) {
		this.employeeEduRepository = employeeEduRepository;
		this.modelMapper = modelMapper;
	}
	
	// 사번으로 page 조회
	public Page<EmployeeEduDetailDto> findByPernr (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("seq").ascending());
		
		return employeeEduRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EmployeeEduDetailDto.class));
	}
	
	// 교육 목록 저장
	@Transactional
	public void saveEduList (String pernr, List<EmployeeEduRequestDto> dtoList) {
		Set<Integer> eduSeqs = new HashSet<>();
		
		List<EmployeeEdu> originalEdu = employeeEduRepository.findByPernr(pernr);
		
		// 순차 검사
		for (EmployeeEduRequestDto dto : dtoList) {
			if (!eduSeqs.add(dto.getSeq())) {
				throw new IllegalArgumentException("순번 '" + dto.getSeq() + "'이(가) 중복으로 입력되었습니다.");
			}
		}
		
		Map<Long, EmployeeEdu> originalMap = originalEdu.stream()
				.collect(Collectors.toMap(EmployeeEdu::getId, edu -> edu));
		
		Map<Long, EmployeeEduRequestDto> dtoMap = dtoList.stream()
				.filter(dto -> dto.getId() != null)
				.collect(Collectors.toMap(EmployeeEduRequestDto::getId, dto -> dto));
		
		try { // 삭제 대상
			List<EmployeeEdu> eduToDelete = originalEdu.stream()
					.filter(edu -> !dtoMap.containsKey(edu.getId()))
					.toList();
			
			if (!eduToDelete.isEmpty()) {
				employeeEduRepository.deleteAllInBatch(eduToDelete);
			}
			
			for (EmployeeEduRequestDto dto : dtoList) {
				if (dto.getId() == null) { // Id가 없으면 신규등록
					employeeEduRepository.save(modelMapper.map(dto, EmployeeEdu.class));
				} else {
					EmployeeEdu employeeEdu = originalMap.get(dto.getId());
					if (employeeEdu != null) {
						employeeEdu.update(dto);
						employeeEduRepository.saveAndFlush(employeeEdu);
					}
				}
			}
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("순번이 중복되었습니다.");
		}
		
	}
}
