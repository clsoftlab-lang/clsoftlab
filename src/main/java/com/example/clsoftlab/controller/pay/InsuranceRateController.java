package com.example.clsoftlab.controller.pay;

import java.time.LocalDate;

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

import com.example.clsoftlab.dto.pay.InsuranceRateDetailDto;
import com.example.clsoftlab.dto.pay.InsuranceRateRequestDto;
import com.example.clsoftlab.dto.pay.InsuranceRateSearchDto;
import com.example.clsoftlab.service.pay.InsuranceRateService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/insurance-rate")
public class InsuranceRateController {

	private final InsuranceRateService insuranceRateService;
	
	public InsuranceRateController(InsuranceRateService insuranceRateService) {
		this.insuranceRateService = insuranceRateService;
	}
	
	// 검색어로 전체 목록 조회
	@GetMapping("")
	public String getInsuranceRateList (@ModelAttribute InsuranceRateSearchDto search, @RequestParam(required =  false, defaultValue = "0") Integer page,
			Model model) {
		int size = 1000;
		
		Page<InsuranceRateDetailDto> insuranceRatePage = insuranceRateService.searchInsuraceRate(search, page, size);
		
		model.addAttribute("insuranceRate", insuranceRatePage.getContent());
		model.addAttribute("insType", search.getInsType());
		model.addAttribute("fromDate", search.getFromDate());
		model.addAttribute("useYn", search.getUseYn());
		model.addAttribute("insuranceRatePage", insuranceRatePage);
		
		return "pay/insurance-rate/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewInsuranceRate (@Valid @RequestBody InsuranceRateRequestDto dto) {
		insuranceRateService.addNewInsuranceRate(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateInsuranceRate (@PathVariable long id, @Valid @RequestBody InsuranceRateRequestDto dto) {
		insuranceRateService.updateInsuranceRate(id, dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 체크
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String insType, @RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, @RequestParam(required = false) Long id) {
		boolean result = insuranceRateService.checkOverlap(insType, fromDate, toDate, id);
		return ResponseEntity.ok(result);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<InsuranceRateDetailDto> findById (@PathVariable Long id) {
		return insuranceRateService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
