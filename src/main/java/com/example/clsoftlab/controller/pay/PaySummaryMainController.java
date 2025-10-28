package com.example.clsoftlab.controller.pay;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
		model.addAttribute("paySummaryEmployeeList", paySummaryMainService.getEmployeeList());
		
		return "pay/pay-summary/list";
	}
	
	// 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updatePaySummary (@Valid @RequestBody PaySummaryMainRequestDto dto) {
		paySummaryMainService.updatePaySummary(dto);
		return ResponseEntity.ok().build();
	}
}
