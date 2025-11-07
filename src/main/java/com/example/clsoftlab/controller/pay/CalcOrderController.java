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

import com.example.clsoftlab.dto.pay.CalcOrderDetailDto;
import com.example.clsoftlab.dto.pay.CalcOrderRequestDto;
import com.example.clsoftlab.service.pay.CalcOrderService;
import com.example.clsoftlab.service.pay.PayItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/calc-order")
public class CalcOrderController {


	private final CalcOrderService calcOrderService;
	private final PayItemService payItemService;
	
	public CalcOrderController(CalcOrderService calcOrderService, PayItemService payItemService) {
		this.calcOrderService = calcOrderService;
		this.payItemService = payItemService;
	}
	
	// 검색어로 목록 조회
	@GetMapping("")
	public String getCalcOrderList (@RequestParam(required = false) List<String> itemCode, @RequestParam(required = false) List<String> groupCode, 
			@RequestParam(required = false) String useYn, @RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		
		int size = 1000;
		
		Page<CalcOrderDetailDto> calcOrderPage = calcOrderService.searchCalcOrder(itemCode, groupCode, useYn, page, size);
		
		model.addAttribute("calcOrder", calcOrderPage.getContent());
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("groupCode", groupCode);
		model.addAttribute("useYn", useYn);
		model.addAttribute("calcOrderPage", calcOrderPage);
		model.addAttribute("searchCalcOrderList", calcOrderService.findAll());
		model.addAttribute("payItemList", payItemService.findAllForSearch());
		
		return "pay/calc-order/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewCalcOrder (@Valid @RequestBody CalcOrderRequestDto dto) {
		calcOrderService.addNewCalcOrder(dto);
		return ResponseEntity.ok().build();
	}
	
	//  기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateCalcOrder (@Valid @RequestBody CalcOrderRequestDto dto) {
		calcOrderService.upateNewCalcOrder(dto);
		return ResponseEntity.ok().build();
	}
	
	// 코드 중복 체크
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String itemCode) {
		boolean result = calcOrderService.checkOverlap(itemCode);
		return ResponseEntity.ok(result);
	}
	
	// 계산 순서 중복 체크
	@GetMapping("/checkOverlap/order")
	public ResponseEntity<Boolean> checkOverlappingOrder (@RequestParam Integer order, @RequestParam String groupCode , @RequestParam(defaultValue = "") String itemCode) {
		boolean result = calcOrderService.checkOverlap(order, groupCode, itemCode);
		return ResponseEntity.ok(result);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<CalcOrderDetailDto> findById (@PathVariable Long id) {
		return calcOrderService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
