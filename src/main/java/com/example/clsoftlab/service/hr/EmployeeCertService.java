package com.example.clsoftlab.service.hr;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.EmployeeCertDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeCertRequestDto;
import com.example.clsoftlab.entity.EmployeeCert;
import com.example.clsoftlab.repository.hr.EmployeeCertRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeCertService {

	private final EmployeeCertRepository employeeCertRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeCertService(EmployeeCertRepository employeeCertRepository, ModelMapper modelMapper) {
		this.employeeCertRepository = employeeCertRepository;
		this.modelMapper = modelMapper;
	}
	
	// 상세 정보 조회
	public Page<EmployeeCertDetailDto> searchEmployeeCert (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("seq").ascending());
		
		return employeeCertRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EmployeeCertDetailDto.class));
	}
	
	// 항목 등록, 수정.
	@Transactional
	public void savedEmployeeCert (List<EmployeeCertRequestDto> dtoList) {
		
			for (EmployeeCertRequestDto dto : dtoList) {
            
	            // ID가 없으면 신규 등록
	            if (dto.getId() == null) {
	                employeeCertRepository.save(modelMapper.map(dto, EmployeeCert.class));
	            } else {  // ID가 있으면 수정
	                // 1. ID로 기존 엔티티를 조회합니다.
	                EmployeeCert employeeCert = employeeCertRepository.findById(dto.getId())
	                		.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id: " + dto.getId()));
	                employeeCert.update(dto);
            }
        }
	}
	
	// 항목 삭제
	
}
