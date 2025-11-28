package com.example.clsoftlab.service.common;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.common.specification.EmployeeMasterSpecs;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeMasterService {

	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
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
	
	// 검색어로 page 조회
	public Page<EmployeeMasterDto> searchEmployee (List<String> pernr, List<String> deptCode, 
			List<String> rankCode, List<String> empStatus,
			int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<EmployeeMaster> spec = Specification.not(null);
		
		spec = spec.and(EmployeeMasterSpecs.withPernr(pernr))
				.and(EmployeeMasterSpecs.withDeptCode(deptCode))
				.and(EmployeeMasterSpecs.withRankCode(rankCode))
				.and(EmployeeMasterSpecs.withEmpStatus(empStatus));
		
		return employeeMasterRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class));
	}
}
