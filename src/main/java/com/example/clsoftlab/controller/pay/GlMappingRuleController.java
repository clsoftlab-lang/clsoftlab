package com.example.clsoftlab.controller.pay;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clsoftlab.dto.pay.GlMappingRuleDetailDto;
import com.example.clsoftlab.dto.pay.GlMappingRuleRequestDto;
import com.example.clsoftlab.dto.pay.GlMappingRuleSearchDto;
import com.example.clsoftlab.service.pay.GlMappingRuleService;
import com.example.clsoftlab.service.pay.PayItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/gl-mapping")
public class GlMappingRuleController {

	private final GlMappingRuleService glMappingRuleService;
	private final PayItemService payItemService;
	
	public GlMappingRuleController(GlMappingRuleService glMappingRuleService, PayItemService payItemService) {
		this.glMappingRuleService = glMappingRuleService;
		this.payItemService = payItemService;
	}
	
	// 검색어로 목록 조회
	@GetMapping("")
	public String getGlMappingRuleList (@ModelAttribute GlMappingRuleSearchDto search, @RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		
		int size = 1000;
		
		Page<GlMappingRuleDetailDto> glMappingRulePage = glMappingRuleService.searchGlMappingRule(search, page, size);
		
		model.addAttribute("glMappingRule", glMappingRulePage.getContent());
		model.addAttribute("itemCode", search.getItemCode());
		model.addAttribute("bizCode", search.getBizCode());
		model.addAttribute("costCntr", search.getCostCntr());
		model.addAttribute("useYn", search.getUseYn());
		model.addAttribute("glMappingRulePage", glMappingRulePage);
		model.addAttribute("payItemList", payItemService.findAllForSearch());
		model.addAttribute("searchPayItemList", glMappingRuleService.getPayItemList());
		model.addAttribute("searchBizCodeList", glMappingRuleService.getBizCodeList());
		model.addAttribute("searchCostCntrList", glMappingRuleService.getCostCntrList());
		
		return "pay/gl-mapping-rule/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewGlMappingRule (@Valid @RequestBody GlMappingRuleRequestDto dto) {
		glMappingRuleService.addNewGlMappingRule(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateGlMappingRule (@Valid @RequestBody GlMappingRuleRequestDto dto) {
		glMappingRuleService.updateGlMappingRule(dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverap (@RequestParam String itemCode, @RequestParam String bizCode, @RequestParam String costCntr) {
		boolean result = glMappingRuleService.checkOverlap(itemCode, bizCode, costCntr);
		return ResponseEntity.ok(result);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<GlMappingRuleDetailDto> findByID (@PathVariable long id) {
		return glMappingRuleService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
			
}
