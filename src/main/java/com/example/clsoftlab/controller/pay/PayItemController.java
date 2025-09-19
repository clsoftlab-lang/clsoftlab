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

import com.example.clsoftlab.dto.pay.PayItemDetailDto;
import com.example.clsoftlab.dto.pay.PayItemListDto;
import com.example.clsoftlab.dto.pay.PayItemRequestDto;
import com.example.clsoftlab.service.pay.PayItemService;

import jakarta.validation.Valid;

/*

프로그램명 : Pay Item Controller

작성자 : 양한재

작성일 : 2025-09-08

수정자 : 

수정일 : 

프로그램 개요 : Pay Item Controller는 급여항목 관련 페이지를 처리하는 컨트롤러 클래스이다.

프로그램 설명 : 

*/

@Controller
@RequestMapping("/pay/pay-item")
public class PayItemController {
	
	private final PayItemService payItemService;
	
	public PayItemController(PayItemService payItemService) {
		this.payItemService = payItemService;
	}
	
	//전체 목록 조회
	@GetMapping("")
	public String payItemList(@RequestParam(defaultValue = "") String itemName, @RequestParam(defaultValue = "") String itemType,
			@RequestParam(defaultValue = "") String useYn, @RequestParam(required = false) Integer page,
			Model model) { 
		
		
		if (page == null) {
			page = 0;
		}
		int size= 1000;
		
		Page<PayItemListDto> itemPage = payItemService.searchPayItem(itemName, itemType, useYn, page, size);
		
		model.addAttribute("payItem", itemPage.getContent());
		model.addAttribute("itemName", itemName);
		model.addAttribute("itemType", itemType);
		model.addAttribute("useYn", useYn);
		model.addAttribute("itemPage", itemPage);
		
		return "pay/pay-item/list";
	}
	
	//급여 항목 추가
	@PostMapping("")
	public ResponseEntity<Void> addNewPayItem (@Valid @RequestBody PayItemRequestDto payItem) {
		
		payItemService.addNewPayItem(payItem);
		return ResponseEntity.ok().build();
	}
	
	// itemCode로 급여항목 검색
	@GetMapping("detail/{itemCode}")
    public ResponseEntity<PayItemDetailDto> findPayItemByCode(@PathVariable String itemCode) {
        
		return payItemService.findByCode(itemCode)
                .map(dto -> ResponseEntity.ok(dto)) 
                .orElse(ResponseEntity.notFound().build());
   }
	
	//급여 항목 수정
	@PutMapping("/{itemCode}")
	public ResponseEntity<Void> updatePayItem(@PathVariable String itemCode, @Valid @RequestBody PayItemRequestDto payItem) {
		
	    payItemService.updatePayItem(itemCode, payItem);
	    return ResponseEntity.ok().build();
	}
	
	// 급여 항목 검색 
	@ResponseBody
	@GetMapping("/list")
	public Page<PayItemListDto> getPayItemList(@RequestParam(defaultValue = "") String itemName, @RequestParam(defaultValue = "") String itemType,
			@RequestParam(defaultValue = "") String useYn, @RequestParam(required = false) Integer page) { 
		
		
		if (page == null) {
			page = 0;
		}
		int size= 1000;
		
		Page<PayItemListDto> itemPage = payItemService.searchPayItem(itemName, itemType, useYn, page, size);
		
		
		return itemPage;
	}
	
	// 급여 항목 검색 (DEDUCT만)
		@ResponseBody
		@GetMapping("/deductList")
		public Page<PayItemListDto> getDeductList(@RequestParam(defaultValue = "") String itemName, @RequestParam(defaultValue = "") String useYn, 
				@RequestParam(required = false) Integer page) { 
			
			
			if (page == null) {
				page = 0;
			}
			int size= 1000;
			
			Page<PayItemListDto> itemPage = payItemService.searchDeduct(itemName, useYn, page, size);
			
			
			return itemPage;
		}

    @GetMapping("test")
    public String payItemTestList(@RequestParam(defaultValue = "") String itemName, @RequestParam(defaultValue = "") String itemType,
                              @RequestParam(defaultValue = "") String useYn, @RequestParam(required = false) Integer page,
                              Model model) {


        if (page == null) {
            page = 0;
        }
        int size= 1000; //필요한 경우만 사용 가능하도록 변경 필요 size가 0일때는 전체 나오도록 개선 필요.

        Page<PayItemListDto> itemPage = payItemService.searchPayItem(itemName, itemType, useYn, page, size);

        model.addAttribute("payItem", itemPage.getContent());
        model.addAttribute("itemName", itemName);
        model.addAttribute("itemType", itemType);
        model.addAttribute("useYn", useYn);
        model.addAttribute("itemPage", itemPage);

        return "pay/pay-item/list_backup";
    }
}
