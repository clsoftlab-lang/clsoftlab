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

import com.example.clsoftlab.dto.pay.CalcOrderDetailDto;
import com.example.clsoftlab.dto.pay.CalcOrderRequestDto;
import com.example.clsoftlab.service.pay.CalcOrderService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/calc-order")
public class CalcOrderController {


	private final CalcOrderService calcOrderService;
	
	public CalcOrderController(CalcOrderService calcOrderService) {
		this.calcOrderService = calcOrderService;
	}
	
	// 검색어로 목록 조회
	@GetMapping("")
	public String getCalcOrderList (@RequestParam(defaultValue = "") String itemCode, @RequestParam(defaultValue = "") String groupCode, 
			@RequestParam(defaultValue = "") String useYn, @RequestParam(required = false) Integer page, Model model) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<CalcOrderDetailDto> calcOrderPage = calcOrderService.searchCalcOrder(itemCode, groupCode, useYn, page, size);
		
		model.addAttribute("calcOrder", calcOrderPage.getContent());
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("groupCode", groupCode);
		model.addAttribute("useYn", useYn);
		model.addAttribute("calcOrderPage", calcOrderPage);
		
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
	@ResponseBody
	@GetMapping("/checkOverlap")
	public boolean checkOverlap (@RequestParam String itemCode) {
		return calcOrderService.checkOverlap(itemCode);
	}
	
	// 계산 순서 중복 체크
	@ResponseBody
	@GetMapping("/checkOverlap/order")
	public boolean checkOverlappingOrder (@RequestParam Integer order, @RequestParam String groupCode , @RequestParam(defaultValue = "") String itemCode) {
		return calcOrderService.checkOverlap(order, groupCode, itemCode);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{itemCode}")
	public ResponseEntity<CalcOrderDetailDto> findById (@PathVariable String itemCode) {
		return calcOrderService.findById(itemCode)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// 급여 항목 순서 검색'
	@ResponseBody
	@GetMapping("/list")
	public Page<CalcOrderDetailDto> getCalcOrderList (@RequestParam(defaultValue = "") String itemCode, @RequestParam(defaultValue = "") String groupCode ,
			@RequestParam(defaultValue = "") String useYn, @RequestParam(required = false) Integer page) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<CalcOrderDetailDto> calcOrderPage = calcOrderService.searchCalcOrder(itemCode, groupCode, useYn, page, size);
		
		return calcOrderPage;
	}
}
