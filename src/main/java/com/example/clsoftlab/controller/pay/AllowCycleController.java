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

import com.example.clsoftlab.dto.pay.AllowCycleDetailDto;
import com.example.clsoftlab.dto.pay.AllowCycleRequestDto;
import com.example.clsoftlab.service.pay.AllowCycleService;
import com.example.clsoftlab.service.pay.PayItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/allow-cycle")
public class AllowCycleController {

	private final AllowCycleService allowCycleService;
	private final PayItemService payItemService;
	
	public AllowCycleController(AllowCycleService allowCycleService, PayItemService payItemService) {
		this.allowCycleService = allowCycleService;
		this.payItemService = payItemService;
	}
	
	// 검색어로 목록 조회
	@GetMapping("")
	public String getAllowCycleList (@RequestParam(required = false) List<String> itemCode, @RequestParam(required = false) List<String> cycle,
			@RequestParam(required = false) String useYn, @RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		int size = 1000;
		
		Page<AllowCycleDetailDto> allowCyclePage = allowCycleService.searchAllowCycle(itemCode, cycle, useYn, page, size);
		
		model.addAttribute("allowCycle", allowCyclePage.getContent());
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("cycle", cycle);
		model.addAttribute("useYn", useYn);
		model.addAttribute("allowCyclePage", allowCyclePage);
		model.addAttribute("payItemList", payItemService.findAllByItemType("PAY"));
		model.addAttribute("searchPayItemList", allowCycleService.getPayItemList());
		
		return "pay/allow-cycle/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewAllowCycle (@Valid @RequestBody AllowCycleRequestDto dto) {
		allowCycleService.addNewAllowCycle(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateAllowCycle (@Valid @RequestBody AllowCycleRequestDto dto) {
		allowCycleService.updateAllowCycle(dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String itemCode) {
		boolean result =  allowCycleService.checkOverlap(itemCode);
		return ResponseEntity.ok(result);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<AllowCycleDetailDto> findById (@PathVariable Long id) {
		return allowCycleService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
