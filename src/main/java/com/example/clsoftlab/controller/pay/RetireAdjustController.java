package com.example.clsoftlab.controller.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clsoftlab.dto.pay.RetireAdjustDetailDto;
import com.example.clsoftlab.dto.pay.RetireAdjustRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.RetireAdjustService;
import com.example.clsoftlab.service.pay.RetireSummaryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/retire-adjust")
public class RetireAdjustController {


	private final RetireAdjustService retireAdjustService;
	private final RetireSummaryService retireSummaryService;
	private final EmployeeMasterService employeeMasterService;
	
	public RetireAdjustController(RetireAdjustService retireAdjustService, RetireSummaryService retireSummaryService,
			EmployeeMasterService employeeMasterService) {
		this.retireAdjustService = retireAdjustService;
		this.retireSummaryService = retireSummaryService;
		this.employeeMasterService = employeeMasterService;
	}
	
	// 검색어로 page 조회
	@GetMapping("")
	public String searchPayAdjust(@RequestParam(required = false) List<String> empNo, @RequestParam(required = false) LocalDate retireDate, 
			@RequestParam(required = false) String adjType, @RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		
		int size = 1000;
		
		Page<RetireAdjustDetailDto> retireAdjustPage = retireAdjustService.searchRetireAdjust(empNo, retireDate, adjType, page, size);
		
		model.addAttribute("retireAdjust", retireAdjustPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("retireDate", retireDate);
		model.addAttribute("adjType", adjType);
		model.addAttribute("retireAdjustPage", retireAdjustPage);
		model.addAttribute("employeeList", employeeMasterService.getRetireList());
		model.addAttribute("retireSummaryList", retireSummaryService.getRetireSummaryList());
		
		return "pay/retire-adjust/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewAdjust (@Valid @RequestBody RetireAdjustRequestDto dto) {
		retireAdjustService.addNewAdjust(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateAdjust (@Valid @RequestBody RetireAdjustRequestDto dto) {
		retireAdjustService.updateAdjust(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAdjust (@PathVariable Long id) {
		retireAdjustService.deleteAdjust(id);
		return ResponseEntity.ok().build();
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<RetireAdjustDetailDto> findById (@PathVariable Long id) {
		return retireAdjustService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
