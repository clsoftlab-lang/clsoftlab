package com.example.clsoftlab.controller.pay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pay/pay-detail")
public class PayDetailController {

	// 미완성
	@GetMapping("")
	public String payDetailList() {
		
		return "pay/pay-detail/list";
	}
}
