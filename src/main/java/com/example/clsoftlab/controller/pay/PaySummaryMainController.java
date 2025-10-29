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

import com.example.clsoftlab.dto.pay.PaySummaryMainDetailDto;
import com.example.clsoftlab.dto.pay.PaySummaryMainRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.PaySummaryMainService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/pay-summary")
public class PaySummaryMainController {

	private final PaySummaryMainService paySummaryMainService;
	private final EmployeeMasterService employeeMasterService;
	
	public PaySummaryMainController(PaySummaryMainService paySummaryMainService, EmployeeMasterService employeeMasterService) {
		this.paySummaryMainService = paySummaryMainService;
		this.employeeMasterService = employeeMasterService;
	}
	
	// 검색어로 page 조회
	@GetMapping("")
	public String getPaySummaryPage (@RequestParam(required = false) String payYm, @RequestParam(required = false) List<String> empNo,
			@RequestParam(required = false) Integer seqNo, @RequestParam(required = false) String isFinal, 
			@RequestParam(required = false) Integer page, Model model ) {
		
		if(page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<PaySummaryMainDetailDto> paySummaryPage = paySummaryMainService.searchPaySummary(payYm, empNo, seqNo, isFinal, page, size);
		
		model.addAttribute("paySummaryMain", paySummaryPage.getContent());
		model.addAttribute("payYm", payYm);
		model.addAttribute("empNo", empNo);
		model.addAttribute("seqNo", seqNo);
		model.addAttribute("isFinal", isFinal);
		model.addAttribute("paySummaryMainPage", paySummaryPage);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("searchEmployeeList", paySummaryMainService.getEmployeeList());
		
		return "pay/pay-summary/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewPaySummary (@Valid @RequestBody PaySummaryMainRequestDto dto) {
		paySummaryMainService.addNewPaySummary(dto);
		return ResponseEntity.ok().build(); 
	}
	
	// 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updatePaySummary (@Valid @RequestBody PaySummaryMainRequestDto dto) {
		paySummaryMainService.updatePaySummary(dto);
		return ResponseEntity.ok().build();
	}
	
	// 세부 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<PaySummaryMainDetailDto> findById (@PathVariable Long id) {
		return paySummaryMainService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// 중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String payYm, @RequestParam String empNo, 
			@RequestParam Integer seqNo, @RequestParam(required = false) Long id) {
		boolean result = paySummaryMainService.checkOverlap(payYm, empNo, seqNo , id);
		return ResponseEntity.ok(result);
	}
}
