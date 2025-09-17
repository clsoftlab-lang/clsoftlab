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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.clsoftlab.dto.pay.PayCycleDetailDto;
import com.example.clsoftlab.dto.pay.PayCycleRequestDto;
import com.example.clsoftlab.service.pay.PayCycleService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/pay-cycle")
public class PayCycleController {

	private PayCycleService payCycleService;
	
	public PayCycleController(PayCycleService payCycleService) {
		this.payCycleService = payCycleService;
	}
	
	// 전체 목록 조회
	@GetMapping("")
	public String getPayCycleList(@RequestParam(defaultValue = "") String jobGroup, @RequestParam(defaultValue = "") String useYn,
			@RequestParam(required = false) Integer page, Model model) {
		
		if (page == null) {
			page = 0;
		}
		int size= 1000;
		
		Page<PayCycleDetailDto> payCyclePage = payCycleService.searchPayCycle(jobGroup, useYn, page, size);
		
		model.addAttribute("payCycle", payCyclePage.getContent());
		model.addAttribute("jobGroup", jobGroup);
		model.addAttribute("useYn", useYn);
		model.addAttribute("payCyclePage", payCyclePage);
		
		return "pay/pay-cycle/list";
	}
	
	// 새 지급 주기 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewPayCycle (@Valid @RequestBody PayCycleRequestDto dto) {
		payCycleService.addNewPayCyle(dto);
		return ResponseEntity.ok().build();
	}
	
	// 특정 지급 주기 수정
	@PutMapping("/{jobGroup}")
	public ResponseEntity<Void> updatePayCycle (@PathVariable String jobGroup,@Valid @RequestBody PayCycleRequestDto dto) {
		
		payCycleService.updatePayCycle(jobGroup, dto);
		return ResponseEntity.ok().build();
	}
	
	//중복 id 체크
	@ResponseBody
	@GetMapping("/checkOverlap/{jobGroup}")
	public long checkOverlappingPayCycle (@PathVariable String jobGroup) {
		
		return payCycleService.countOverlappingPayCycle(jobGroup);
	}
	
	// detail 조회
	@GetMapping("/detail/{jobGroup}")
	public ResponseEntity<PayCycleDetailDto> findById (@PathVariable String jobGroup) {
		
		return payCycleService.findById(jobGroup)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
