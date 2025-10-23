package com.example.clsoftlab.controller.pay;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.clsoftlab.dto.pay.BackpayRuleDetailDto;
import com.example.clsoftlab.dto.pay.BackpayRuleRequestDto;
import com.example.clsoftlab.service.pay.BackpayRuleService;
import com.example.clsoftlab.service.pay.PayItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/backpay-rule")
public class BackpayRuleController {

	private final BackpayRuleService backpayRuleService;
	private final PayItemService payItemService;
	
	public BackpayRuleController(BackpayRuleService backpayRuleService, PayItemService payItemService) {
		this.backpayRuleService = backpayRuleService;
		this.payItemService = payItemService;
	}
	
	// 전체 목록
	@GetMapping("")
	public String getBackpayRuleList(@RequestParam(required = false) List<String> appliedItemCode, @RequestParam(required = false) List<String> baseItemCode,
			@RequestParam(required = false) Integer page, Model model) {
		
		if(page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<BackpayRuleDetailDto> backpayRulePage = backpayRuleService.searchBackpayRule(appliedItemCode, baseItemCode, page, size);
		
		model.addAttribute("backpayRule", backpayRulePage.getContent());
		model.addAttribute("appliedItemCode", appliedItemCode);
		model.addAttribute("baseItemCode", baseItemCode);
		model.addAttribute("backpayRulePage", backpayRulePage);
		model.addAttribute("payItemList", payItemService.findAll());
		
		return "pay/backpay-rule/list";
	}
	
	// 새 소급 규칙 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewBackpayRule (@Valid @RequestBody BackpayRuleRequestDto dto) {
		
		backpayRuleService.addNewBackpayRule(dto);
		return ResponseEntity.ok().build();
	}
	
	// 소급 규칙 수정
	@PutMapping("{ruleId}")
	public ResponseEntity<Void> updateBackPayRule (@Valid @RequestBody BackpayRuleRequestDto dto, @PathVariable long ruleId) {
		
		backpayRuleService.updateBackpayRule(ruleId, dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사용
	@ResponseBody
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlappingbackpayRule (@RequestParam String appliedItemCode, @RequestParam String baseItemCode) {
		
		boolean result = backpayRuleService.countOverlappingbackpayRule(appliedItemCode, baseItemCode);
		
		return ResponseEntity.ok(result);
	}
	
	// detail 조회용
	@GetMapping("/detail/{ruleId}")
	public ResponseEntity<BackpayRuleDetailDto> findById (@PathVariable long ruleId) {
		
		return backpayRuleService.findById(ruleId)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
