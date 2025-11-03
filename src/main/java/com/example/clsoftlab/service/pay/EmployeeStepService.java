package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.EmployeeStepDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeStepRequestDto;
import com.example.clsoftlab.entity.EmployeeStep;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.EmployeeStepRepository;
import com.example.clsoftlab.repository.pay.specification.EmployeeStepSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeStepService {

	private final EmployeeStepRepository employeeStepRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeStepService(EmployeeStepRepository employeeStepRepository, EmployeeMasterRepository employeeMasterRepository,
			ModelMapper modelMapper) {
		this.employeeMasterRepository = employeeMasterRepository;
		this.employeeStepRepository = employeeStepRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 page 조회
	public Page<EmployeeStepDetailDto> searchEmployeeStep (List<String> pernr, List<String> gradeCode, Integer fromDate,
			Integer toDate, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<EmployeeStep> spec = Specification.not(null);
		
		LocalDate searchDateFrom = null;
        LocalDate searchDateTo = null;

        // 1. 'From' 년도를 'YYYY-01-01'로 변환
        if (fromDate != null) {
            searchDateFrom = LocalDate.of(fromDate, Month.JANUARY, 1);
        }

        // 2. 'To' 년도를 'YYYY-12-31'로 변환
        if (toDate != null) {
            // LocalDate.of(searchDto.getSearchYearTo(), Month.DECEMBER, 31) 보다 이 방식이 더 안전합니다.
            searchDateTo = LocalDate.of(toDate, Month.DECEMBER, 1)
                                .with(TemporalAdjusters.lastDayOfMonth()); 
        }
		
		spec = spec.and(EmployeeStepSpecs.withPernr(pernr))
				.and(EmployeeStepSpecs.withGradeCode(gradeCode))
				.and(EmployeeStepSpecs.withDateRange(searchDateFrom, searchDateTo));
		
		return employeeStepRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, EmployeeStepDetailDto.class));
	}
	
	// 신규 항목 등록
	@Transactional
	public void addNewStep (EmployeeStepRequestDto dto) {
		EmployeeStep employeeStep = modelMapper.map(dto, EmployeeStep.class);
		employeeStep.setEmployee(employeeMasterRepository.getReferenceById(dto.getPernr()));
		employeeStepRepository.save(employeeStep);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateStep (EmployeeStepRequestDto dto) {
		EmployeeStep employeeStep = employeeStepRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		
		employeeStep.update(dto);
	}
	
	// 상세 정보 조회
	public Optional<EmployeeStepDetailDto> findById (Long id) {
		return employeeStepRepository.findById(id)
				.map(i -> modelMapper.map(i, EmployeeStepDetailDto.class));
	}
	
	// 검색용 사원 리스트 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return employeeStepRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// 검색용 직군 리스트 조회
	public List<String> getGradeCodeList () {
		return employeeStepRepository.getGradeCodeList();
	}
	
	// 기간 중복 검사
	public boolean checkOverlap (String pernr, LocalDate fromDate, LocalDate toDate, Long id) {
		if (id == null) {
			return employeeStepRepository.checkOverlap(pernr, fromDate, toDate);
		} else {
			return employeeStepRepository.checkOverlap(pernr, fromDate, toDate, id);
		}
	}
}
