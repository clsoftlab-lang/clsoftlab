package com.example.clsoftlab.controller.pay;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clsoftlab.dto.pay.LogPayCalcDetailDto;
import com.example.clsoftlab.service.pay.LogPayCalcService;

@Controller
@RequestMapping("/pay/log-pay-calc")
public class LogPayCalcController {

	private final LogPayCalcService logPayCalcService;

	public LogPayCalcController(LogPayCalcService logPayCalcService) {
		this.logPayCalcService = logPayCalcService;
	}
	
	// 검색어로 page 조회
	@GetMapping("")
	public String searchLog (@RequestParam(required = false) List<String> payYm, @RequestParam(required = false) List<String> empNo,
			@RequestParam(required = false) List<String> itemCode, @RequestParam(required = false) List<Integer> seqNo,
			@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		int size = 1000;
		
		Page<LogPayCalcDetailDto> logPayCalcPage = logPayCalcService.searchLog(payYm, empNo, itemCode, seqNo, page, size);
		
		model.addAttribute("logPayCalc", logPayCalcPage.getContent());
		model.addAttribute("payYm", payYm);
		model.addAttribute("empNo", empNo);
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("seqNo", seqNo);
		model.addAttribute("logPayCalcPage", logPayCalcPage);
		model.addAttribute("searchPayYmList", logPayCalcService.getPayYmList());
		model.addAttribute("searchEmployeeList", logPayCalcService.getEmployeeList());
		model.addAttribute("searchPayItemList", logPayCalcService.getPayItemList());
		model.addAttribute("searchSeqNoList", logPayCalcService.getSeqNoList());
		
		return "pay/log-pay-calc/list";
	}
}
