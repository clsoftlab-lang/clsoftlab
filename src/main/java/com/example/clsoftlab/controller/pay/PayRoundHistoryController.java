package com.example.clsoftlab.controller.pay;

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
	public String getPayRoundHistoryList (@RequestParam(defaultValue = "") String empNo, @RequestParam(defaultValue = "") String payYm,
			@RequestParam(defaultValue = "")String itemCode, @RequestParam(required = false) Integer page, Model model) {
		
		if(page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<PayRoundHistoryDetailDto> payRoundHistoryPage = payRoundHistoryService.searchPayRoundHistory(empNo, payYm, itemCode, page, size);
		
		model.addAttribute("payRoundHistory", payRoundHistoryPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("payYm", payYm);
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("payRoundHistoryPage", payRoundHistoryPage);
		
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
