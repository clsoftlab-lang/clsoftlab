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

import com.example.clsoftlab.dto.pay.PayItemDetailDto;
import com.example.clsoftlab.dto.pay.PayItemListDto;
import com.example.clsoftlab.dto.pay.PayItemRequestDto;
import com.example.clsoftlab.service.pay.PayItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/pay-item")
public class PayItemController {
	
	private final PayItemService payItemService;
	
	public PayItemController(PayItemService payItemService) {
		this.payItemService = payItemService;
	}
	
	//전체 목록 조회
	@GetMapping("")
	public String payItemList(@RequestParam(required = false) List<String> itemCode, @RequestParam(required = false) List<String> itemTypes,
			@RequestParam(required = false) String useYn, @RequestParam(required = false, defaultValue = "0") Integer page,
			Model model) { 
		
		int size= 1000;
		
		Page<PayItemListDto> itemPage = payItemService.searchPayItem(itemCode, itemTypes, useYn, page, size);
		
		model.addAttribute("payItem", itemPage.getContent());
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("itemTypes", itemTypes);
		model.addAttribute("useYn", useYn);
		model.addAttribute("itemPage", itemPage);
		model.addAttribute("searchPayItemList", payItemService.findAllForSearch());
		
		return "pay/pay-item/list";
	}
	
	//급여 항목 추가
	@PostMapping("")
	public ResponseEntity<Void> addNewPayItem (@Valid @RequestBody PayItemRequestDto payItem) {
		
		payItemService.addNewPayItem(payItem);
		return ResponseEntity.ok().build();
	}
	
	//급여 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updatePayItem(@Valid @RequestBody PayItemRequestDto payItem) {
		
		payItemService.updatePayItem(payItem);
		return ResponseEntity.ok().build();
	}
	
	// itemCode로 급여항목 검색
	@GetMapping("detail/{itemCode}")
    public ResponseEntity<PayItemDetailDto> findPayItemByCode(@PathVariable String itemCode) {
        
		return payItemService.findByCode(itemCode)
                .map(dto -> ResponseEntity.ok(dto)) 
                .orElse(ResponseEntity.notFound().build());
   }
	
	// 중복검사
	@GetMapping("/checkOverlap/{itemCode}")
	public ResponseEntity<Boolean> checkOverlap (@PathVariable String itemCode) {
		boolean result = payItemService.checkOverlap(itemCode);
		return ResponseEntity.ok(result);
	}
	
	
	// 급여 항목 검색 (DEDUCT만)
	@GetMapping("/deductList")
	public ResponseEntity<Page<PayItemListDto>>  getDeductList(@RequestParam(required = false) List<String> itemCode, @RequestParam(required = false) String useYn, 
			@RequestParam(required = false, defaultValue = "0") Integer page) { 
		
		int size= 1000;
		
		Page<PayItemListDto> itemPage = payItemService.searchDeduct(itemCode, useYn, page, size);
		
		
		return ResponseEntity.ok(itemPage);
	}

		@GetMapping("test")
		public String payItemTestList(@RequestParam(defaultValue = "") String itemName, @RequestParam(defaultValue = "") String itemType,
				@RequestParam(defaultValue = "") String useYn, @RequestParam(required = false) Integer page, Model model) {


			if (page == null) {
				page = 0;
			}
			int size = 1000; //필요한 경우만 사용 가능하도록 변경 필요 size가 0일때는 전체 나오도록 개선 필요.

			Page<PayItemListDto> itemPage = payItemService.searchPayItem(itemName, itemType, useYn, page, size);

			model.addAttribute("payItem", itemPage.getContent());
			model.addAttribute("itemName", itemName);
			model.addAttribute("itemType", itemType);
			model.addAttribute("useYn", useYn);
			model.addAttribute("itemPage", itemPage);

			return "pay/pay-item/list_backup";
		}
}
