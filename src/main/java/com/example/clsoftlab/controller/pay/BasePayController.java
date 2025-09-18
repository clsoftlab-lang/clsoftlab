package com.example.clsoftlab.controller.pay;

import java.time.LocalDate;

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

import com.example.clsoftlab.dto.pay.BasePayDetailDto;
import com.example.clsoftlab.dto.pay.BasePayRequestDto;
import com.example.clsoftlab.service.pay.BasePayService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/base-pay")
public class BasePayController {

	private final BasePayService basePayService;
	
	public BasePayController(BasePayService basePayService) {
		this.basePayService = basePayService;
	}
	
	// 전체 목록 조회
	@GetMapping("")
	public String getBasePayList (@RequestParam(defaultValue = "") String empNo, @RequestParam(defaultValue = "") String baseUnit,
			@RequestParam(required = false) Integer page, Model model) {
		
		if (page == null) {
			page = 0;
		}
		int size = 1000;
		
		Page<BasePayDetailDto> basePayPage = basePayService.searchBasePay(empNo, baseUnit, page, size);
		
		model.addAttribute("basePay", basePayPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("baseUnit", baseUnit);
		model.addAttribute("basePayPage", basePayPage);
		
		return "/pay/base-pay/list";
	}
	
	// 신규 기준급여 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewBasePay (@Valid @RequestBody BasePayRequestDto dto) {
		basePayService.addNewBasePay(dto);
		
		return ResponseEntity.ok().build();
	}
	
	// 기준 급여 수정
	@PutMapping("/{payId}")
	public ResponseEntity<Void> updateBasePay (@PathVariable long payId,@Valid @RequestBody BasePayRequestDto dto) {
		
		basePayService.updateBasePay(payId, dto);
		return ResponseEntity.ok().build();
	}
	
	// 기간 중복 검사
	@ResponseBody
	@GetMapping("/checkOverlap") 
	public long checkOverlappingBasePay (@RequestParam String empNo, @RequestParam LocalDate fromDate,
			@RequestParam LocalDate toDate) {
		
		return basePayService.countOverlappingBasePay(empNo, fromDate, toDate);
	}
	
	// 기간 중복 검사 (수정용) 
	@ResponseBody
	@GetMapping("/checkOverlap/update") 
	public long checkOverlappingBasePayForUpdate (@RequestParam String empNo, @RequestParam long payId, 
			@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate) {
		
		return basePayService.countOverlappingBasePay(empNo, payId, fromDate, toDate);
	}
	
	// 기준 급여 디테일 정보
	@GetMapping("/detail/{payId}")
	public ResponseEntity<BasePayDetailDto> findById (@PathVariable long payId) {
		return basePayService.findById(payId)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
