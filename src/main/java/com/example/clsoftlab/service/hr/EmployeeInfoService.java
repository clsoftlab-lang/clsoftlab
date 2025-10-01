package com.example.clsoftlab.service.hr;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.EmployeeInfoDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeInfoRequestDto;
import com.example.clsoftlab.entity.EmployeeInfo;
import com.example.clsoftlab.repository.hr.EmployeeInfoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeInfoService {

	private final EmployeeInfoRepository employeeInfoRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeInfoService(EmployeeInfoRepository employeeInfoRepository, ModelMapper modelMapper) {
		this.employeeInfoRepository = employeeInfoRepository;
		this.modelMapper = modelMapper;
	}
	
	// 상세 정보 조회
	public Optional<EmployeeInfoDetailDto> findById (String pernr) {
		return employeeInfoRepository.findById(pernr)
				.map(i -> modelMapper.map(i, EmployeeInfoDetailDto.class));
	}
	
	// 항목 수정
	@Transactional
	public void updateEmployeeInfo (String pernr, EmployeeInfoRequestDto dto) {
		EmployeeInfo employeeInfo = employeeInfoRepository.findById(pernr)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. pernr : " + pernr));
		
		employeeInfo.update(dto);
	}
}
