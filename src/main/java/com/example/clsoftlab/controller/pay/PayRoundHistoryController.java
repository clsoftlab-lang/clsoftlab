package com.example.clsoftlab.controller.pay;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clsoftlab.dto.pay.PayRoundHistoryDetailDto;
import com.example.clsoftlab.service.pay.PayRoundHistoryService;

@Controller
@RequestMapping("/pay/pay-round-history")
public class PayRoundHistoryController {
	
	private final PayRoundHistoryService payRoundHistoryService;
	
	public PayRoundHistoryController(PayRoundHistoryService payRoundHistoryService) {
		this.payRoundHistoryService = payRoundHistoryService;
	}
	
	// 검색어로 전체 목록 조회
	@GetMapping("")
	public String getPayRoundHistoryList (@RequestParam(required = false) List<String> empNo, @RequestParam(required = false) String payYm,
			@RequestParam(required = false)List<String> itemCode, @RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		
		int size = 1000;
		
		Page<PayRoundHistoryDetailDto> payRoundHistoryPage = payRoundHistoryService.searchPayRoundHistory(empNo, payYm, itemCode, page, size);
		
		model.addAttribute("payRoundHistory", payRoundHistoryPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("payYm", payYm);
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("payRoundHistoryPage", payRoundHistoryPage);
		model.addAttribute("searchEmployeeList", payRoundHistoryService.getEmployeeList());
		model.addAttribute("searchPayItemList", payRoundHistoryService.getPayItemList());
		
		return "pay/pay-round-history/list";
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<PayRoundHistoryDetailDto> findById (@PathVariable long id) {
		return payRoundHistoryService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.ok().build());
	}

}
