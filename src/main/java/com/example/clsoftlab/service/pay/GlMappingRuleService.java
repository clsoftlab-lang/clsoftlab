package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.GlMappingRuleDetailDto;
import com.example.clsoftlab.dto.pay.GlMappingRuleRequestDto;
import com.example.clsoftlab.dto.pay.GlMappingRuleSearchDto;
import com.example.clsoftlab.dto.pay.PayItemSearchDto;
import com.example.clsoftlab.entity.GlMappingRule;
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
		Pageable pageable = PageRequest.of(page, size);
		Specification<GlMappingRule> spec = Specification.not(null);
		
		spec = spec.and(GlMappingRuleSpecs.withItemCode(search.getItemCode()))
			.and(GlMappingRuleSpecs.withBizCode(search.getBizCode()))
			.and(GlMappingRuleSpecs.withCostCntr(search.getCostCntr()))
			.and(GlMappingRuleSpecs.withUseYn(search.getUseYn()));
		
		return glMappingRuleRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, GlMappingRuleDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewGlMappingRule (GlMappingRuleRequestDto dto) {
		GlMappingRule glMappingRule = modelMapper.map(dto, GlMappingRule.class);
		glMappingRule.setPayItem(payItemRepository.getReferenceById(dto.getItemCode()));
		glMappingRuleRepository.save(glMappingRule);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateGlMappingRule (GlMappingRuleRequestDto dto) {
		GlMappingRule glMappingRule = glMappingRuleRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		
		glMappingRule.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String itemCode, String bizCode, String costCntr) {
		return glMappingRuleRepository.existsByPayItem_ItemCodeAndBizCodeAndCostCntr(itemCode, bizCode, costCntr);
	}
	
	// 상세 정보 조회
	public Optional<GlMappingRuleDetailDto> findById (long id) {
		return glMappingRuleRepository.findById(id)
				.map(i -> modelMapper.map(i, GlMappingRuleDetailDto.class));
	}
	
	// 검색용 payItem 리스트 조회
	public List<PayItemSearchDto> getPayItemList () {
		return glMappingRuleRepository.getPayItemList().stream()
				.map(i -> modelMapper.map(i, PayItemSearchDto.class))
				.toList();
	}
	
	// 검색용 bizCode 리스트 조회
	public List<String> getBizCodeList () {
		return glMappingRuleRepository.getBizCodeList();
	}
	
	// 검색용 costCntr 리스트 조회
	public List<String> getCostCntrList () {
		return glMappingRuleRepository.getCostCntrList();
	}
}
