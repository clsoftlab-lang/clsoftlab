package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.PayCycleDetailDto;
import com.example.clsoftlab.dto.pay.PayCycleRequestDto;
import com.example.clsoftlab.entity.PayCycle;
import com.example.clsoftlab.repository.pay.PayCycleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PayCycleService {

	private final PayCycleRepository payCycleRepository;
	private final ModelMapper modelMapper;
	
	public PayCycleService(PayCycleRepository payCycleRepository, ModelMapper modelMapper) {
		this.payCycleRepository = payCycleRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<PayCycleDetailDto> searchPayCycle (String jobGroup, String useYn, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("jobGroup"));
		
		return payCycleRepository.searchPayCycle(jobGroup, useYn, pageable).
				map(i -> modelMapper.map(i, PayCycleDetailDto.class));
		
	}
	
	// 코드 중복 검사
	public long countOverlappingPayCycle (String jobGroup) {
		return payCycleRepository.countOverlappingPayCycle(jobGroup);
	}
	
	// 새 지급 주기 등록
	@Transactional
	public void addNewPayCyle (PayCycleRequestDto dto) {
		payCycleRepository.save(modelMapper.map(dto, PayCycle.class));
	}
	
	// id로 특정 지급 주기 찾기
	public Optional<PayCycleDetailDto> findById (String jobGroup) {
		return payCycleRepository.findById(jobGroup).map(i -> modelMapper.map(i, PayCycleDetailDto.class));
	}
	
	// 지급 주기 수정
	@Transactional
	public void updatePayCycle (String jobGroup,PayCycleRequestDto dto) {
		PayCycle payCycle = payCycleRepository.findById(jobGroup)
				.orElseThrow(() -> new EntityNotFoundException("해당 코드를 찾을 수 없습니다: " + jobGroup));
		
		payCycle.update(dto);
	}
}
