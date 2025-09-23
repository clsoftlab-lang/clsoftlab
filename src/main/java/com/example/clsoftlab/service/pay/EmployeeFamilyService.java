package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.EmployeeFamilyDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeFamilyRequestDto;
import com.example.clsoftlab.entity.EmployeeFamily;
import com.example.clsoftlab.repository.pay.EmployeeFamilyRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeFamilyService {

	private final EmployeeFamilyRepository employeeFamilyRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeFamilyService(EmployeeFamilyRepository employeeFamilyRepository, ModelMapper modelMapper) {
		this.employeeFamilyRepository = employeeFamilyRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<EmployeeFamilyDetailDto> searchEmployeeFamily (String empNo, String familyName, String familyType, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("empNo"));
		
		return employeeFamilyRepository.findByEmpNoContainingAndFamilyNameContainingAndFamilyTypeContaining(empNo, familyName, familyType, pageable)
				.map(i -> modelMapper.map(i, EmployeeFamilyDetailDto.class));
	}
	
	// 새 사원 가족 등록
	@Transactional
	public void addNewEmployeeFamily (EmployeeFamilyRequestDto dto) {
		employeeFamilyRepository.save(modelMapper.map(dto, EmployeeFamily.class));
	}
	
	// 기존 사원 가족 수정
	@Transactional
	public void updateEmployeeFamily (long id, EmployeeFamilyRequestDto dto) {
		EmployeeFamily employeeFamily = employeeFamilyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : "  + id));
		employeeFamily.update(dto);
	}
	
	// 중복 체크
	public boolean checkOverlap (String empNo, Integer familySeq) {
		return employeeFamilyRepository.existsByEmpNoAndFamilySeq(empNo, familySeq);
	}
	
	// 세부 정보 조회
	public Optional<EmployeeFamilyDetailDto> findById (long id) {
		return employeeFamilyRepository.findById(id)
				.map(i -> modelMapper.map(i, EmployeeFamilyDetailDto.class));
	}
}
