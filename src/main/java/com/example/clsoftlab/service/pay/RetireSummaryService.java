package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.RetireSummaryDetailDto;
import com.example.clsoftlab.dto.pay.RetireSummaryRequestDto;
import com.example.clsoftlab.entity.RetireSummary;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.RetireSummaryRepository;
import com.example.clsoftlab.repository.pay.specification.RetireSummarySpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RetireSummaryService {

	private final RetireSummaryRepository retireSummaryRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public RetireSummaryService(RetireSummaryRepository retireSummaryRepository, EmployeeMasterRepository employeeMasterRepository,
			ModelMapper modelMapper) {
		this.retireSummaryRepository = retireSummaryRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 page 조회
	public Page<RetireSummaryDetailDto> searchRetireSummary (List<String> pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<RetireSummary> spec = Specification.not(null);
		
		spec = spec.and(RetireSummarySpecs.withPernr(pernr));
		
		return retireSummaryRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, RetireSummaryDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewRetireSummary (RetireSummaryRequestDto dto) {
		RetireSummary retireSummary = modelMapper.map(dto, RetireSummary.class);
		retireSummary.setEmployee(employeeMasterRepository.getReferenceById(dto.getPernr()));
		
		retireSummaryRepository.save(retireSummary);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateRetireSummary (RetireSummaryRequestDto dto) {
		RetireSummary retireSummary = retireSummaryRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		
		retireSummary.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String pernr) {
		return retireSummaryRepository.existsByEmployee_Pernr(pernr);
	}
	
	// 디테일 정보 조회
	public Optional<RetireSummaryDetailDto> findById (Long id) {
		return retireSummaryRepository.findById(id)
				.map(i -> modelMapper.map(i, RetireSummaryDetailDto.class));
	}
	
	
	// 검색용 사번 list 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return retireSummaryRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// 검색용 reitreSummary List 조회
	public List<RetireSummaryDetailDto> getRetireSummaryList () {
		return retireSummaryRepository.getRetireSummaryList().stream()
				.map(i -> modelMapper.map(i, RetireSummaryDetailDto.class))
				.toList();
	}
}
