package com.example.clsoftlab.service.common;

import java.util.List;
import java.util.Optional;

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
	
	// 전체 List 조회 (검색용)
	public List<EmployeeMasterDto> findAll () {
		return employeeMasterRepository.findAllByOrderByName().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// retire List 조회
	public List<EmployeeMasterDto> getRetireList () {
		return employeeMasterRepository.findByRetireDateIsNotNull().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// 상세 정보 조회
	public Optional<EmployeeMasterDto> findByPernr (String pernr) {
		return employeeMasterRepository.findById(pernr)
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class));
	}
}
