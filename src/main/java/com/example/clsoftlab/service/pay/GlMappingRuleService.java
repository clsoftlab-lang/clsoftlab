package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.GlMappingRuleDetailDto;
import com.example.clsoftlab.dto.pay.GlMappingRuleRequestDto;
import com.example.clsoftlab.dto.pay.GlMappingRuleSearchDto;
import com.example.clsoftlab.entity.GlMappingRule;
import com.example.clsoftlab.entity.PayItem;
import com.example.clsoftlab.repository.pay.GlMappingRuleRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;
import com.example.clsoftlab.repository.pay.specification.GlMappingRuleSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class GlMappingRuleService {

	private final GlMappingRuleRepository glMappingRuleRepository;
	private final PayItemRepository payItemRepository;
	private final ModelMapper modelMapper;
	
	public GlMappingRuleService(GlMappingRuleRepository glMappingRuleRepository, PayItemRepository payItemRepository ,
			ModelMapper modelMapper) {
		this.glMappingRuleRepository = glMappingRuleRepository;
		this.payItemRepository = payItemRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<GlMappingRuleDetailDto> searchGlMappingRule (GlMappingRuleSearchDto search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("itemCode"));
		Specification<GlMappingRule> spec = Specification.not(null);
		
		spec = spec.and(GlMappingRuleSpecs.withItemCode(search.getItemCode()));
		spec = spec.and(GlMappingRuleSpecs.withBizCode(search.getBizCode()));
		spec = spec.and(GlMappingRuleSpecs.withCostCntr(search.getCostCntr()));
		spec = spec.and(GlMappingRuleSpecs.withUseYn(search.getUseYn()));
		
		return glMappingRuleRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, GlMappingRuleDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewGlMappingRule (GlMappingRuleRequestDto dto) {
		GlMappingRule glMappingRule = modelMapper.map(dto, GlMappingRule.class);
		
		PayItem payItem = payItemRepository.findById(dto.getItemCode())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. itemCode : " + dto.getItemCode()));
		
		glMappingRule.setPayItem(payItem);
		glMappingRuleRepository.save(glMappingRule);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateGlMappingRule (long id, GlMappingRuleRequestDto dto) {
		GlMappingRule glMappingRule = glMappingRuleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		glMappingRule.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String itemCode, String bizCode, String costCntr) {
		return glMappingRuleRepository.existsByItemCodeAndBizCodeAndCostCntr(itemCode, bizCode, costCntr);
	}
	
	// 상세 정보 조회
	public Optional<GlMappingRuleDetailDto> findById (long id) {
		return glMappingRuleRepository.findById(id)
				.map(i -> modelMapper.map(i, GlMappingRuleDetailDto.class));
	}
}
