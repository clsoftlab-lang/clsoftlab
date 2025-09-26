package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.AllowCycleDetailDto;
import com.example.clsoftlab.dto.pay.AllowCycleRequestDto;
import com.example.clsoftlab.entity.AllowCycle;
import com.example.clsoftlab.entity.PayItem;
import com.example.clsoftlab.repository.pay.AllowCycleRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AllowCycleService {

	private final AllowCycleRepository allowCycleRepository;
	private final PayItemRepository payItemRepository;
	private final ModelMapper modelMapper;
	private final EntityManager entityManager;
	
	public AllowCycleService(AllowCycleRepository allowCycleRepository, PayItemRepository payItemRepository, 
			ModelMapper modelMapper, EntityManager entityManager) {
		this.allowCycleRepository = allowCycleRepository;
		this.payItemRepository = payItemRepository;
		this.modelMapper = modelMapper;
		this.entityManager = entityManager;
	}
	
	// 검색어로 목록 조회
	public Page<AllowCycleDetailDto> searchAllowCycle (String itemCode, String cycle, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("itemCode"));
		
		return allowCycleRepository.findByItemCodeContainingAndCycleContainingAndUseYnContaining(itemCode, cycle, useYn, pageable)
				.map(i -> modelMapper.map(i, AllowCycleDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewAllowCycle (AllowCycleRequestDto dto) {
		if (allowCycleRepository.existsById(dto.getItemCode())) {
			 throw new IllegalStateException("이미 존재하는 항목 코드입니다: " + dto.getItemCode());
		}
		
		AllowCycle allowCycle = modelMapper.map(dto, AllowCycle.class);
		
		PayItem payItem = payItemRepository.findById(dto.getItemCode())
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 항목 입니다. itemCode : " + dto.getItemCode()));
		
		allowCycle.setPayItem(payItem);
		
		entityManager.persist(allowCycle);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateAllowCycle (AllowCycleRequestDto dto) {
		AllowCycle allowCycle = allowCycleRepository.findById(dto.getItemCode())
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 항목 입니다. itemCode : " + dto.getItemCode()));
		
		allowCycle.update(dto);
	}
	
	// 코드 중복 검사
	public boolean checkOverlap (String itemCode) {
		return allowCycleRepository.existsById(itemCode);
	}
	
	// 상세 정보 조회
	public Optional<AllowCycleDetailDto> findById (String itemCode) {
		return allowCycleRepository.findById(itemCode)
				.map(i -> modelMapper.map(i, AllowCycleDetailDto.class));
	}
}
