package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.BasePayDetailDto;
import com.example.clsoftlab.dto.pay.BasePayRequestDto;
import com.example.clsoftlab.entity.BasePay;
import com.example.clsoftlab.repository.pay.BasePayRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BasePayService {

	private final BasePayRepository basePayRepository;
	private final ModelMapper modelMapper;
	
	public BasePayService(BasePayRepository basePayRepository, ModelMapper modelMapper) {
		this.basePayRepository = basePayRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 목록 조회
	public Page<BasePayDetailDto> searchBasePay (String empNo, String baseUnit, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("empNo"));
		
		return basePayRepository.searchBasePay(empNo, baseUnit, pageable)
				.map(i -> modelMapper.map(i, BasePayDetailDto.class));
	}
	
	// 중복 기간 검사
	public long countOverlappingBasePay (String empNo, LocalDate fromDate, LocalDate toDate) {
		return basePayRepository.countOverlappingBasePay(empNo, fromDate, toDate);
	}
	
	// 중복 기간 검사 (수정용)
	public long countOverlappingBasePay (String empNo, long payId, LocalDate fromDate, LocalDate toDate) {
		return basePayRepository.countOverlappingBasePayForUpdate(empNo, payId, fromDate, toDate);
	}
	
	// 새 기준급여 저장
	@Transactional
	public void addNewBasePay (BasePayRequestDto dto) {
		basePayRepository.save(modelMapper.map(dto, BasePay.class));
	}
	
	// 기준급여 수정
	@Transactional
	public void updateBasePay (long payId, BasePayRequestDto dto) {
		BasePay basePay = basePayRepository.findById(payId)
				.orElseThrow(() -> new EntityNotFoundException("해당 기준 급여를 찾을 수 없습니다."));
		
		basePay.update(dto);
	}
	
	// id로 특정 기준급여 detail 찾기
	public Optional<BasePayDetailDto> findById (long payId) {
		return basePayRepository.findById(payId).map(i -> modelMapper.map(i, BasePayDetailDto.class));
	}
}
