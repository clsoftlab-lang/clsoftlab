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

import com.example.clsoftlab.dto.hr.EmployeeCareerDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeCareerRequestDto;
import com.example.clsoftlab.entity.EmployeeCareer;
import com.example.clsoftlab.repository.hr.EmployeeCareerRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeCareerService {

	private final EmployeeCareerRepository employeeCareerRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeCareerService(EmployeeCareerRepository employeeCareerRepository, ModelMapper modelMapper) {
		this.employeeCareerRepository = employeeCareerRepository;
		this.modelMapper = modelMapper;
	}
	
	// 사번으로 경력 검색
	public Page<EmployeeCareerDetailDto> findByPernr (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("seq").ascending());
		return employeeCareerRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EmployeeCareerDetailDto.class));
	}
	
	// 경력 리스트 저장
	@Transactional
	public void saveEmployeeCareerList (String pernr, List<EmployeeCareerRequestDto> dtoList) {
		
		Set<Integer> careerSeqs = new HashSet<>();
		
		List<EmployeeCareer> originalCareer = employeeCareerRepository.findByPernr(pernr);
		
		// 순차 검사
		for (EmployeeCareerRequestDto dto : dtoList) {
			if (!careerSeqs.add(dto.getSeq())) {
				throw new IllegalArgumentException("순번 '" + dto.getSeq() + "'이(가) 중복으로 입력되었습니다.");
			}
		}
		
		Map<Long, EmployeeCareer> originalMap = originalCareer.stream()
				.collect(Collectors.toMap(EmployeeCareer::getId, career -> career));
		
		Map<Long, EmployeeCareerRequestDto> dtoMap = dtoList.stream()
				.filter(dto -> dto.getId() != null)
				.collect(Collectors.toMap(EmployeeCareerRequestDto::getId, dto -> dto));
		
		try {
			// 삭제 대상 정리
			List<EmployeeCareer> careerToDelete = originalCareer.stream()
					.filter(career -> !dtoMap.containsKey(career.getId()))
					.toList();
			
			if (!careerToDelete.isEmpty()) {
				employeeCareerRepository.deleteAllInBatch(careerToDelete);
			}
			
			for (EmployeeCareerRequestDto dto : dtoList) {
				
				// Id가 없으면 신규 등록
				if (dto.getId() == null) {
					employeeCareerRepository.save(modelMapper.map(dto, EmployeeCareer.class));
				} else { // Id가 있으면 수정
					EmployeeCareer employeeCareer = originalMap.get(dto.getId());
					if (employeeCareer != null) {
						employeeCareer.update(dto);
						employeeCareerRepository.saveAndFlush(employeeCareer);
					}
					
				}
			}
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("순번이 중복되었습니다.");
		}
	}
}
