package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.StandardHoursEmployeeDetailDto;
import com.example.clsoftlab.dto.pay.StandardHoursEmployeeRequestDto;
import com.example.clsoftlab.entity.StandardHoursEmployee;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.StandardHoursEmployeeRepository;
import com.example.clsoftlab.repository.pay.specification.StandardHoursEmployeeSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class StandardHoursEmployeeService {

	private final StandardHoursEmployeeRepository standardHoursEmployeeRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public StandardHoursEmployeeService(StandardHoursEmployeeRepository standardHoursEmployeeRepository , EmployeeMasterRepository employeeMasterRepository,
			ModelMapper modelMapper) {
		this.standardHoursEmployeeRepository = standardHoursEmployeeRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<StandardHoursEmployeeDetailDto> searchStandardHoursEmployee (String calYm, List<String> empNo, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("calYm").descending());
		Specification<StandardHoursEmployee> spec = Specification.not(null);
		
		spec = spec.and(StandardHoursEmployeeSpecs.withCalYm(calYm))
				.and(StandardHoursEmployeeSpecs.withEmpNo(empNo));
		
		return standardHoursEmployeeRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, StandardHoursEmployeeDetailDto.class));
	}
	
	// 새 근로시간 등록
	@Transactional
	public void addNewStandardHoursEmployee (StandardHoursEmployeeRequestDto dto) {
		StandardHoursEmployee standardHoursEmployee = modelMapper.map(dto, StandardHoursEmployee.class);
		standardHoursEmployee.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		standardHoursEmployeeRepository.save(standardHoursEmployee);
	}
	
	// 근로시간 수정
	@Transactional
	public void updateStandardHoursEmployee (long id, StandardHoursEmployeeRequestDto dto) {
		StandardHoursEmployee standardHoursEmployee = standardHoursEmployeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 사원별 월 소정 근로시간을  찾을 수 없습니다. id : "+ id));
		standardHoursEmployee.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String calYm, String empNo) {
		return standardHoursEmployeeRepository.existsByCalYmAndEmployee_Pernr(calYm, empNo);
	}
	
	// 디테일 정보
	public Optional<StandardHoursEmployeeDetailDto> findById (long id) {
		return standardHoursEmployeeRepository.findById(id)
				.map(i -> modelMapper.map(i, StandardHoursEmployeeDetailDto.class));
	}
	
	// emp list 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return standardHoursEmployeeRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
}
