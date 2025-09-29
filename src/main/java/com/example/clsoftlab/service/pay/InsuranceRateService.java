package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.InsuranceRateDetailDto;
import com.example.clsoftlab.dto.pay.InsuranceRateRequestDto;
import com.example.clsoftlab.dto.pay.InsuranceRateSearchDto;
import com.example.clsoftlab.entity.InsuranceRate;
import com.example.clsoftlab.repository.pay.InsuranceRateRepository;
import com.example.clsoftlab.repository.pay.specification.InsuranceRateSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class InsuranceRateService {

	private final InsuranceRateRepository insuranceRateRepository;
	private final ModelMapper modelMapper;
	
	public InsuranceRateService(InsuranceRateRepository insuranceRateRepository, ModelMapper modelMapper) {
		this.insuranceRateRepository = insuranceRateRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 전체 목록 조회
	public Page<InsuranceRateDetailDto> searchInsuraceRate (InsuranceRateSearchDto search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("insType"));
		Specification<InsuranceRate> spec = Specification.not(null);
		
		spec = spec.and(InsuranceRateSpecs.withInsType(search.getInsType()));
		spec = spec.and(InsuranceRateSpecs.lessThanOrEqualToFromDate(search.getFromDate()));
		spec = spec.and(InsuranceRateSpecs.greaterThanOrEqualToFromDate(search.getFromDate()));
		spec = spec.and(InsuranceRateSpecs.withUseYn(search.getUseYn()));
		
		return insuranceRateRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, InsuranceRateDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewInsuranceRate (InsuranceRateRequestDto dto) {
		insuranceRateRepository.save(modelMapper.map(dto, InsuranceRate.class));
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateInsuranceRate (long id, InsuranceRateRequestDto dto) {
		InsuranceRate insuranceRate = insuranceRateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		insuranceRate.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String insType, LocalDate fromDate, LocalDate toDate) {
		return insuranceRateRepository.checkOverlap(insType, fromDate, toDate);
	}
	
	// 중복 검사 (수정용)
	public boolean checkOverlap (String insType, LocalDate fromDate, LocalDate toDate, Long id) {
		return insuranceRateRepository.checkOverlap(insType, fromDate, toDate, id);
	}
	
	// 상세 정보 조회
	public Optional<InsuranceRateDetailDto> findById (long id) {
		return insuranceRateRepository.findById(id)
				.map(i -> modelMapper.map(i, InsuranceRateDetailDto.class));
	}
}
