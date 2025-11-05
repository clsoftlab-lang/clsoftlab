package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.controller.pay.BackpayRuleController;
import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.EmployeeFamilyDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeFamilyRequestDto;
import com.example.clsoftlab.entity.EmployeeFamily;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.EmployeeFamilyRepository;
import com.example.clsoftlab.repository.pay.specification.EmployeeFamilySpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeFamilyService {


	private final EmployeeFamilyRepository employeeFamilyRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeFamilyService(EmployeeFamilyRepository employeeFamilyRepository, EmployeeMasterRepository employeeMasterRepository,
			ModelMapper modelMapper, BackpayRuleController backpayRuleController) {
		this.employeeFamilyRepository = employeeFamilyRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<EmployeeFamilyDetailDto> searchEmployeeFamily (List<String> empNo, String familyName, List<String> familyType, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<EmployeeFamily> spec = Specification.not(null);
		
		spec = spec.and(EmployeeFamilySpecs.withEmpNo(empNo))
				.and(EmployeeFamilySpecs.withFamilyName(familyName))
				.and(EmployeeFamilySpecs.withFamilyType(familyType));
		
		return employeeFamilyRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, EmployeeFamilyDetailDto.class));
	}
	
	// 새 사원 가족 등록
	@Transactional
	public void addNewEmployeeFamily (EmployeeFamilyRequestDto dto) {
		EmployeeFamily employeeFamily = modelMapper.map(dto, EmployeeFamily.class);
		employeeFamily.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		employeeFamilyRepository.save(employeeFamily);
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
		return employeeFamilyRepository.existsByEmployee_PernrAndFamilySeq(empNo, familySeq);
	}
	
	// 세부 정보 조회
	public Optional<EmployeeFamilyDetailDto> findById (long id) {
		return employeeFamilyRepository.findById(id)
				.map(i -> modelMapper.map(i, EmployeeFamilyDetailDto.class));
	}
	
	// 검색용 사번 list 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return employeeFamilyRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
}
