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

import com.example.clsoftlab.dto.pay.RetireSummaryDetailDto;
import com.example.clsoftlab.dto.pay.RetireSummaryRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.RetireSummaryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/retire-summary")
public class RetireSummaryController {

	private final RetireSummaryService retireSummaryService;
	private final EmployeeMasterService employeeMasterService;
	
	public RetireSummaryController(RetireSummaryService retireSummaryService, EmployeeMasterService employeeMasterService) {
		this.retireSummaryService = retireSummaryService;
		this.employeeMasterService = employeeMasterService;
	}
	// 검색어로 page 조회
	@GetMapping("")
	public String searchRetireSummary (@RequestParam(required = false) List<String> pernr, @RequestParam(required = false, defaultValue = "0") Integer page,
			Model model) {
		int size = 1000;
		
		Page<RetireSummaryDetailDto> retireSummaryPage = retireSummaryService.searchRetireSummary(pernr, page, size);
		
		model.addAttribute("retireSummary", retireSummaryPage.getContent());
		model.addAttribute("pernr", pernr);
		model.addAttribute("retireSummaryPage", retireSummaryPage);
		model.addAttribute("employeeList", employeeMasterService.getRetireList());
		model.addAttribute("searchEmployeeList", retireSummaryService.getEmployeeList());
		
		return "pay/retire-summary/list";
		
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewSummary (@Valid @RequestBody RetireSummaryRequestDto dto) {
		retireSummaryService.addNewRetireSummary(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateSummary (@Valid @RequestBody RetireSummaryRequestDto dto) {
		retireSummaryService.updateRetireSummary(dto);
		return ResponseEntity.ok().build();
	}
	
	// 사번 중복 검사
	@GetMapping("checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String pernr) {
		boolean result = retireSummaryService.checkOverlap(pernr);
		return ResponseEntity.ok(result);
	}
	
	// 디테일 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<RetireSummaryDetailDto> findById (@PathVariable Long id) {
		return retireSummaryService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
