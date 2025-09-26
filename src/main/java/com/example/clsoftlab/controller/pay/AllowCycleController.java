package com.example.clsoftlab.controller.pay;

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

import com.example.clsoftlab.dto.pay.AllowCycleDetailDto;
import com.example.clsoftlab.dto.pay.AllowCycleRequestDto;
import com.example.clsoftlab.service.pay.AllowCycleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/allow-cycle")
public class AllowCycleController {

	private final AllowCycleService allowCycleService;
	
	public AllowCycleController(AllowCycleService allowCycleService) {
		this.allowCycleService = allowCycleService;
	}
	
	// 검색어로 목록 조회
	@GetMapping("")
	public String getAllowCycleList (@RequestParam(defaultValue = "") String itemCode, @RequestParam(defaultValue = "") String cycle,
			@RequestParam(defaultValue = "") String useYn, @RequestParam(required = false) Integer page, Model model) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<AllowCycleDetailDto> allowCyclePage = allowCycleService.searchAllowCycle(itemCode, cycle, useYn, page, size);
		
		model.addAttribute("allowCycle", allowCyclePage.getContent());
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("cycle", cycle);
		model.addAttribute("useYn", useYn);
		model.addAttribute("allowCyclePage", allowCyclePage);
		
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
	@ResponseBody
	@GetMapping("/checkOverlap")
	public boolean checkOverlap (@RequestParam String itemCode) {
		return allowCycleService.checkOverlap(itemCode);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{itemCode}")
	public ResponseEntity<AllowCycleDetailDto> findById (@PathVariable String itemCode) {
		return allowCycleService.findById(itemCode)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
