package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.BackpayRuleDetailDto;
import com.example.clsoftlab.dto.pay.BackpayRuleRequestDto;
import com.example.clsoftlab.entity.BackpayRule;
import com.example.clsoftlab.entity.PayItem;
import com.example.clsoftlab.repository.pay.BackpayRuleRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Service
public class BackpayRuleService {

	private final BackpayRuleRepository backpayRuleRepository;
	private final PayItemRepository payItemRepository;
	private final ModelMapper modelMapper;
	
	public BackpayRuleService(BackpayRuleRepository backpayRuleRepository, PayItemRepository payItemRepository, 
			ModelMapper modelMapper) {
		this.backpayRuleRepository = backpayRuleRepository;
		this.payItemRepository = payItemRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 목록 조회
	public Page<BackpayRuleDetailDto> searchBackpayRule (String appliedItemCode, String baseItemCode, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("appliedItem.itemCode"));
		
		return backpayRuleRepository.searchBackpayRule(appliedItemCode, baseItemCode, pageable)
				.map(i -> modelMapper.map(i, BackpayRuleDetailDto.class));
	}
	
	// 소급 규칙 중복 검사
	public long countOverlappingbackpayRule (String appliedItemCode, String baseItemCode) {
		return backpayRuleRepository.countOverlappingRules(appliedItemCode, baseItemCode);
	}
	
	// 새 소급 규칙 등록
	@Transactional
	public void addNewBackpayRule (BackpayRuleRequestDto dto) {
		
		PayItem appliedItem = payItemRepository.findById(dto.getAppliedItemCode())
				.orElseThrow(() -> new EntityNotFoundException("적용 항목코드를 찾을 수 없습니다: " + dto.getAppliedItemCode()));
		
		PayItem baseItem = payItemRepository.findById(dto.getBaseItemCode())
				.orElseThrow(() -> new EntityNotFoundException("기준 항목코드를 찾을 수 없습니다: " + dto.getBaseItemCode()));
		
		BackpayRule backPayRule = new BackpayRule();
		
		backPayRule.setAppliedItem(appliedItem);
		backPayRule.setBaseItem(baseItem);
		backPayRule.setRuleType(dto.getRuleType());
		backPayRule.setBackPercent(dto.getBackPercent());
		backPayRule.setUseYn(dto.getUseYn());
		backPayRule.setNote(dto.getNote());
		
		backpayRuleRepository.save(backPayRule);
	}
	
	// ruleId로 특정 backPay 검색
	public Optional<BackpayRuleDetailDto> findById (long ruleId) {
		return backpayRuleRepository.findById(ruleId).map(i -> modelMapper.map(i, BackpayRuleDetailDto.class));
	}
	
	// 소급 규칙 수정
	@Transactional
	public void updateBackpayRule (long ruleId, BackpayRuleRequestDto dto) {
		
		BackpayRule backpayRule = backpayRuleRepository.findById(ruleId)
                .orElseThrow(() -> new EntityNotFoundException("규칙을 찾을 수 없습니다: " + ruleId));
		
		backpayRule.update(dto);
	}
}
