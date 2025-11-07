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

import com.example.clsoftlab.dto.pay.SimplifiedTaxTableDetailDto;
import com.example.clsoftlab.dto.pay.SimplifiedTaxTableRequestDto;
import com.example.clsoftlab.repository.pay.BackpayRuleRepository;
import com.example.clsoftlab.service.pay.SimplifiedTaxTableService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/simplified-tax-table")
public class SimplifiedTaxTableController {


	private final SimplifiedTaxTableService simplifiedTaxTableService;
	
	public SimplifiedTaxTableController(SimplifiedTaxTableService simplifiedTaxTableService, BackpayRuleRepository backpayRuleRepository) {
		this.simplifiedTaxTableService = simplifiedTaxTableService;
	}
	
	// 검색어로 page 조회
	@GetMapping("")
	public String searchTaxTable (@RequestParam(required = false) String year, @RequestParam(required = false) Integer familyCount,
			@RequestParam(required = false) Long incomeAmtFrom, @RequestParam(required = false) Long incomeAmtTo,
			@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		
		int size = 1000;
		
		Page<SimplifiedTaxTableDetailDto> simplifiedTaxTablePage = simplifiedTaxTableService.searchTaxTable(year, familyCount, incomeAmtFrom, incomeAmtTo, page, size);
		
		model.addAttribute("simplifiedTaxTable", simplifiedTaxTablePage.getContent());
		model.addAttribute("year", year);
		model.addAttribute("familyCount", familyCount);
		model.addAttribute("incomeAmtFrom", incomeAmtFrom);
		model.addAttribute("incomeAmtTo", incomeAmtTo);
		model.addAttribute("simplifiedTaxTablePage", simplifiedTaxTablePage);
		
		return "pay/simplified-tax-table/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewTaxTable (@Valid @RequestBody SimplifiedTaxTableRequestDto dto) {
		simplifiedTaxTableService.addNewTaxTable(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateTaxTable (@Valid @RequestBody SimplifiedTaxTableRequestDto dto) {
		simplifiedTaxTableService.updateTaxTable(dto);
		return ResponseEntity.ok().build();
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<SimplifiedTaxTableDetailDto> findById (@PathVariable Long id) {
		return simplifiedTaxTableService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// 중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String year, @RequestParam Integer familyCount, @RequestParam Long incomeAmt) {
		boolean result = simplifiedTaxTableService.checkOverlap(year, familyCount, incomeAmt);
		return ResponseEntity.ok(result);
	}
}
