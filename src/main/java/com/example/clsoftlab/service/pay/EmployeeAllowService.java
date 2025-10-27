package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
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
import com.example.clsoftlab.dto.pay.EmployeeAllowDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeAllowRequestDto;
import com.example.clsoftlab.dto.pay.PayItemSearchDto;
import com.example.clsoftlab.entity.EmployeeAllow;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.EmployeeAllowRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;
import com.example.clsoftlab.repository.pay.specification.EmployeeAllowSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeAllowService {

	private final EmployeeAllowRepository employeeAllowRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final PayItemRepository payItemRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeAllowService(EmployeeAllowRepository employeeAllowRepository, EmployeeMasterRepository employeeMasterRepository,
			PayItemRepository payItemRepository, ModelMapper modelMapper) {
		this.employeeAllowRepository = employeeAllowRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.payItemRepository = payItemRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 page 조회
	public Page<EmployeeAllowDetailDto> searchAllow (List<String> empNo, List<String> itemCode, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("employeeMaster.name"));
		Specification<EmployeeAllow> spec = Specification.not(null);
		
		spec = spec.and(EmployeeAllowSpecs.withEmpNo(empNo))
				.and(EmployeeAllowSpecs.withItemCode(itemCode))
				.and(EmployeeAllowSpecs.withUseYn(useYn));
		
		return employeeAllowRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, EmployeeAllowDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewAllow (EmployeeAllowRequestDto dto) {
		EmployeeAllow employeeAllow = modelMapper.map(dto, EmployeeAllow.class);
		employeeAllow.setEmployeeMaster(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		employeeAllow.setPayItem(payItemRepository.getReferenceById(dto.getItemCode()));
		
		employeeAllowRepository.save(employeeAllow);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateAllow (Long id, EmployeeAllowRequestDto dto) {
		EmployeeAllow employeeAllow = employeeAllowRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		employeeAllow.update(dto);
	}
	
	// 상세 정보 조회
	public Optional<EmployeeAllowDetailDto> findById (Long id) {
		return employeeAllowRepository.findById(id)
				.map(i -> modelMapper.map(i, EmployeeAllowDetailDto.class));
	}
	
	// 중복 검사
	public boolean checkOverlap (String empNo, String itemCode, LocalDate fromDate, LocalDate toDate) {
		return employeeAllowRepository.checkOverlap(empNo, itemCode, fromDate, toDate);
	}
	
	// 중복 검사 (수정시)
	public boolean checkOverlap (String empNo, String itemCode, LocalDate fromDate, LocalDate toDate, Long id) {
		return employeeAllowRepository.checkOverlap(empNo, itemCode, fromDate, toDate, id);
	}
	
	// 사번 list 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return employeeAllowRepository.findEmployees().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// payItem List 조회
	public List<PayItemSearchDto> getPayItemList () {
		return employeeAllowRepository.findPayItem().stream()
				.map(i -> modelMapper.map(i, PayItemSearchDto.class))
				.toList();
	}
	
}
