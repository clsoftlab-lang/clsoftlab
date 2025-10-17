package com.example.clsoftlab.service.hr;

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

import com.example.clsoftlab.dto.hr.EmployeeWorkplaceDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeWorkplaceRequestDto;
import com.example.clsoftlab.entity.BizPlace;
import com.example.clsoftlab.entity.EmployeeWorkplace;
import com.example.clsoftlab.repository.hr.BizPlaceRepository;
import com.example.clsoftlab.repository.hr.EmployeeWorkplaceRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeWorkplaceService {

	private final EmployeeWorkplaceRepository employeeWorkplaceRepository;
	private final BizPlaceRepository bizPlaceRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeWorkplaceService(EmployeeWorkplaceRepository employeeWorkplaceRepository, BizPlaceRepository bizPlaceRepository,
			ModelMapper modelMapper) {
		this.employeeWorkplaceRepository = employeeWorkplaceRepository;
		this.bizPlaceRepository = bizPlaceRepository;
		this.modelMapper = modelMapper;
	}
	
	// 사번으로 page 조회
	public Page<EmployeeWorkplaceDetailDto> findByPernr (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("startDate").ascending());
		
		return employeeWorkplaceRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EmployeeWorkplaceDetailDto.class));
	}
	
	// 근무지 목록 저장
	@Transactional
	public void saveWorkplaceList (String pernr, List<EmployeeWorkplaceRequestDto> dtoList) {
		validateWorkplaceRules(dtoList); // 조건 검사
		
		Set<String> workLocCodes = dtoList.stream()
                .map(EmployeeWorkplaceRequestDto::getWorkLocCode)
                .collect(Collectors.toSet());
		
		Map<String, BizPlace> bizplaceMap = bizPlaceRepository.findAllById(workLocCodes).stream()
                .collect(Collectors.toMap(BizPlace::getBizCode, bp -> bp));
		
		List<EmployeeWorkplace> originalList = employeeWorkplaceRepository.findByPernr(pernr);
		
		Map<Long, EmployeeWorkplace> originalMap = originalList.stream()
				.collect(Collectors.toMap(EmployeeWorkplace::getId, place -> place));
		
		Map<Long, EmployeeWorkplaceRequestDto> dtoMap = dtoList.stream()
				.filter(dto -> dto.getId()!= null)
				.collect(Collectors.toMap(EmployeeWorkplaceRequestDto::getId, dto -> dto));
		
		try { // 삭제 대상
			List<EmployeeWorkplace> placeToDelete = originalList.stream()
					.filter(place -> !dtoMap.containsKey(place.getId()))
					.toList();
			
			if (!placeToDelete.isEmpty()) {
				employeeWorkplaceRepository.deleteAllInBatch(placeToDelete);
			}
			
			for(EmployeeWorkplaceRequestDto dto : dtoList) {
				
				BizPlace bizplace = bizplaceMap.get(dto.getWorkLocCode());
	            if (bizplace == null) {
	                throw new EntityNotFoundException("존재하지 않는 근무지 코드입니다: " + dto.getWorkLocCode());
	            }
				if (dto.getId() == null) { // id가 없을시 등록
					EmployeeWorkplace employeeWorkplace = modelMapper.map(dto, EmployeeWorkplace.class);
					employeeWorkplace.setBizplace(bizplace);
					employeeWorkplaceRepository.save(employeeWorkplace);
				} else {
					EmployeeWorkplace employeeWorkplace = originalMap.get(dto.getId());
					
					if (employeeWorkplace != null) {
						employeeWorkplace.update(dto);
						employeeWorkplace.setBizplace(bizplace);
						employeeWorkplaceRepository.saveAndFlush(employeeWorkplace);
					}
				}
			}
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("순번이 중복되었습니다.");
		}
		
	}
	
	// 조건 검사
	private void validateWorkplaceRules(List<EmployeeWorkplaceRequestDto> dtoList) {
        // 규칙 1: 현재 근무지('Y')는 1건만 허용
        if (dtoList.stream().filter(dto -> "Y".equals(dto.getCurrentYn())).count() > 1) {
            throw new IllegalArgumentException("현재 근무지는 하나만 지정할 수 있습니다.");
        }

        // 규칙 2: 종료일자는 시작일자보다 이후여야 함
        for (EmployeeWorkplaceRequestDto dto : dtoList) {
            if (dto.getEndDate() != null && dto.getStartDate().isAfter(dto.getEndDate())) {
                throw new IllegalArgumentException("종료일자는 시작일자보다 빠를 수 없습니다.");
            }
        }
    }
}
