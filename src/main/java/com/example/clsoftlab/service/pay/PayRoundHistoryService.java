package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.PayRoundHistoryDetailDto;
import com.example.clsoftlab.repository.pay.PayRoundHistoryRepository;

@Service
public class PayRoundHistoryService {

	private final PayRoundHistoryRepository payRoundHistoryRepository;
	private final ModelMapper modelMapper;
	
	public PayRoundHistoryService(PayRoundHistoryRepository payRoundHistoryRepository, ModelMapper modelMapper) {
		this.payRoundHistoryRepository = payRoundHistoryRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 목록 조회
	public Page<PayRoundHistoryDetailDto> searchPayRoundHistory (String empNo, String payYm, String itemCode, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("empNo"));
		
		return payRoundHistoryRepository.findByEmpNoContainingAndPayYmContainingAndItemCodeContaining(empNo, payYm, itemCode, pageable)
				.map(i -> modelMapper.map(i, PayRoundHistoryDetailDto.class));
	}
	
	// 상세 정보 조회
	public Optional<PayRoundHistoryDetailDto> findById (long id) {
		return payRoundHistoryRepository.findById(id)
				.map(i -> modelMapper.map(i, PayRoundHistoryDetailDto.class));
	}
}
