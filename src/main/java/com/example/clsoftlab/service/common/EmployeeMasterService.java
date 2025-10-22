package com.example.clsoftlab.service.common;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;

@Service
public class EmployeeMasterService {

	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeMasterService(EmployeeMasterRepository employeeMasterRepository, ModelMapper modelMapper) {
		this.employeeMasterRepository = employeeMasterRepository;
		this.modelMapper = modelMapper;
	}
	
	// 전체 List 조회
	public List<EmployeeMasterDto> findAll () {
		return employeeMasterRepository.findAll().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
}
