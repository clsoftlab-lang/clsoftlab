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

import com.example.clsoftlab.dto.pay.PayRuleDetailDto;
import com.example.clsoftlab.dto.pay.PayRuleListDto;
import com.example.clsoftlab.dto.pay.PayRuleRequestDto;
import com.example.clsoftlab.entity.PayRule;
import com.example.clsoftlab.repository.pay.PayRuleRepository;
import com.example.clsoftlab.repository.pay.specification.PayRuleSpecs;

import jakarta.transaction.Transactional;

@Service
public class PayRuleService {

	private final PayRuleRepository payRuleRepository;
	private final ModelMapper modelMapper;
	
	public PayRuleService(PayRuleRepository payRuleRepository, ModelMapper modelMapper) {
		this.payRuleRepository = payRuleRepository;
		this.modelMapper = modelMapper;
	}
	
	// 계산 규칙 목록 검색
	public Page<PayRuleListDto> searchPayRule (List<String> itemCode, List<String> ruleType, String useYn, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("itemCode"));
		Specification<PayRule> spec = Specification.not(null);
		
		spec = spec.and(PayRuleSpecs.withItemCode(itemCode))
				.and(PayRuleSpecs.withRuleType(ruleType))
				.and(PayRuleSpecs.withUseYn(useYn));
		return payRuleRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, PayRuleListDto.class));
	}
	
	// 계산 규칙 중복 검사
	public long countOverlappingRules (String itemCode, LocalDate fromDate, LocalDate toDate) {
		return payRuleRepository.countOverlappingRules(itemCode, fromDate, toDate);
	}
	
	// 계산 규칙 중복 검사(수정용)
	public long countOverlappingRulesForUpdate (String itemCode, long ruleId, LocalDate fromDate, LocalDate toDate) {
		return payRuleRepository.countOverlappingRulesForUpdate(itemCode, ruleId, fromDate, toDate);
	}
	
	// 새로운 계산 규칙 추가
	@Transactional
	public void addNewPayRule(PayRuleRequestDto payRule) {
		
		payRuleRepository.save(modelMapper.map(payRule, PayRule.class));
		return;
	}
	
	// ruleId로 규칙을 찾은후, 이름과 같이 detailDto로 반환.
	public Optional<PayRuleDetailDto> findById (long ruleId) {
		
		Optional<PayRule> payRule = payRuleRepository.findById(ruleId);
		
		return payRule.map(pr -> {
			
			PayRuleDetailDto dto = modelMapper.map(pr, PayRuleDetailDto.class);
			
			if (pr.getPayItem() != null) {
	            dto.setItemName(pr.getPayItem().getItemName());
	        }
			
			return dto;
		});
		
	}
	
	// 계산 규칙 수정
	@Transactional
	public void updatePayRule (long ruleId, PayRuleRequestDto dto) {
		
		PayRule payRule = payRuleRepository.findById(ruleId)
				.orElseThrow(() -> new IllegalArgumentException("해당 항목을 찾을 수 없습니다. ruleId=" + ruleId));
		
		payRule.update(dto);
	}
}
