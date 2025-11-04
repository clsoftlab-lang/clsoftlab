package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.EmployeeWithholdRateDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeWithholdRateRequestDto;
import com.example.clsoftlab.entity.EmployeeWithholdRate;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.EmployeeWithholdRateRepository;
import com.example.clsoftlab.repository.pay.specification.EmployeeWithholdRateSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeWithholdRateService {

	private final EmployeeWithholdRateRepository employeeWithholdRateRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeWithholdRateService(EmployeeWithholdRateRepository employeeWithholdRateRepository, EmployeeMasterRepository employeeMasterRepository
			, ModelMapper modelMapper) {
		this.employeeMasterRepository = employeeMasterRepository;
		this.employeeWithholdRateRepository = employeeWithholdRateRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 page 조회
	public Page<EmployeeWithholdRateDetailDto> searchWithholdRate (List<String> empNo, LocalDate fromDate
			,LocalDate toDate, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<EmployeeWithholdRate> spec = Specification.not(null);
		
		spec = spec.and(EmployeeWithholdRateSpecs.withEmpNo(empNo))
				.and(EmployeeWithholdRateSpecs.withDateRange(fromDate, toDate));
		
		return employeeWithholdRateRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, EmployeeWithholdRateDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewWithholdRae (EmployeeWithholdRateRequestDto dto) {
		EmployeeWithholdRate employeeWithholdRate = modelMapper.map(dto, EmployeeWithholdRate.class);
		employeeWithholdRate.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		employeeWithholdRateRepository.save(employeeWithholdRate);
	}
	
	//기존 항목 수정
	@Transactional
	public void updateWithholdRate (EmployeeWithholdRateRequestDto dto) {
		EmployeeWithholdRate employeeWithholdRate = employeeWithholdRateRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		employeeWithholdRate.update(dto);
	}
	
	//상세 정보 조회
	public Optional<EmployeeWithholdRateDetailDto> findById (long id) {
		return employeeWithholdRateRepository.findById(id)
				.map(i -> modelMapper.map(i, EmployeeWithholdRateDetailDto.class));
	}
	
	// 검색용 사원 리스트 조회
	public List<EmployeeMasterDto> getEmployeeList() {
		return employeeWithholdRateRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// 기간 중복 검사
	public boolean checkOverlap (String empNo, LocalDate fromDate, LocalDate toDate, Long id) {
		if (id == null) {
			return employeeWithholdRateRepository.checkOverlap(empNo, fromDate, toDate);
		} else {
			return employeeWithholdRateRepository.checkOverlap(empNo, fromDate, toDate, id);
		}
	}
}
