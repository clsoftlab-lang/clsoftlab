package com.example.clsoftlab.controller.pay;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.clsoftlab.dto.pay.PayDeductReasonDetailDto;
import com.example.clsoftlab.dto.pay.PayDeductReasonRequestDto;
import com.example.clsoftlab.service.pay.PayDeductReasonService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/pay-deduct")
public class PayDeductReasonController {

	private final PayDeductReasonService payDeductReasonService;
	
	public PayDeductReasonController(PayDeductReasonService payDeductReasonService) {
		this.payDeductReasonService = payDeductReasonService;
	}
	
	// 검색어로 전체 조회
	@GetMapping("")
	public String getPayDeductReasonList (@RequestParam(defaultValue = "") String empNo, @RequestParam(defaultValue = "") String payYm,
			@RequestParam(defaultValue = "") String itemCode, @RequestParam(required = false) Integer page,
			Model model) {
		
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<PayDeductReasonDetailDto> PayDeductPage = payDeductReasonService.searchPayDeductReason(empNo, payYm, itemCode, size, page);
		
		model.addAttribute("payDeductReason", PayDeductPage.getContent());
		model.addAttribute("payYm", payYm);
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("payDeductPage", PayDeductPage);
		
		return "/pay/pay-deduct/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewPayDeductReason (@Valid @RequestBody PayDeductReasonRequestDto dto) {
		payDeductReasonService.addNewPayDeductReason(dto);
		return ResponseEntity.ok().build();
	}
	
	// 항목 수정
	@PutMapping("/{id}")
	public ResponseEntity<Void> updatePayDeductReason (@PathVariable long id, @Valid @RequestBody PayDeductReasonRequestDto dto) {
		payDeductReasonService.updatePayDeductReason(id, dto);
		return ResponseEntity.ok().build();
	}
	
	// 항목 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePayDeductReason (@PathVariable long id) {
		payDeductReasonService.deletePayDeductReason(id);
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사
	@ResponseBody
	@GetMapping("/checkOverlap")
	public boolean checkOverlappingPayDeductReason (@RequestParam String empNo, @RequestParam String payYm,
			@RequestParam String itemCode, @RequestParam Integer seqNo) {
		return payDeductReasonService.checkOverlappingPayDeductReason(empNo, payYm, seqNo, itemCode);
	}
	
	// 중복 검사
	@ResponseBody
	@GetMapping("/checkOverlap/update")
	public boolean checkOverlappingPayDeductReasonForUpdate (@RequestParam String empNo, @RequestParam String payYm,
			@RequestParam String itemCode, @RequestParam Integer seqNo, @RequestParam Long id) {
		return payDeductReasonService.checkOverlappingPayDeductReason(empNo, payYm, seqNo, itemCode, id);
	}
	
	//디테일 정보
	@GetMapping("/detail/{id}")
	public ResponseEntity<PayDeductReasonDetailDto> findById (@PathVariable long id) {
		return payDeductReasonService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
