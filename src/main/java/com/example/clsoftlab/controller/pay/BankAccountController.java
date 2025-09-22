package com.example.clsoftlab.controller.pay;

import java.time.LocalDate;

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

import com.example.clsoftlab.dto.pay.BankAccountDetailDto;
import com.example.clsoftlab.dto.pay.BankAccountRequestDto;
import com.example.clsoftlab.service.pay.BankAccountService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/bank-account")
public class BankAccountController {
	
	private final BankAccountService bankAccountService;
	
	public BankAccountController(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	}

	// 전체 목록 조회
	@GetMapping("")
	public String getBankAccountList (@RequestParam(defaultValue = "") String empNo, @RequestParam(defaultValue = "") String accountType,
			@RequestParam(defaultValue = "") String useYn, @RequestParam(required = false) Integer page , Model model) {
		
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<BankAccountDetailDto> bankAccountPage = bankAccountService.searchBankAccount(empNo, accountType, useYn, page, size);
		
		model.addAttribute("bankAccount", bankAccountPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("accountType", accountType);
		model.addAttribute("useYn", useYn);
		model.addAttribute("bankAccountPage", bankAccountPage);
		
		return "pay/bank-account/list";
	}
	
	// 새 계좌 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewBankAccount (@Valid @RequestBody BankAccountRequestDto dto) {
		bankAccountService.addNewBankAccount(dto);
		return ResponseEntity.ok().build();
	}
	
	// 특정 계좌 정보 수정
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateBankAccount (@PathVariable long id, @Valid @RequestBody BankAccountRequestDto dto) {
		bankAccountService.updateBankAccount(id, dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 체크
	@ResponseBody
	@GetMapping("/checkOverlap")
	public boolean checkOverlap (@RequestParam String empNo, @RequestParam String accountType, @RequestParam String accountNo, 
			@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate) {
		return bankAccountService.checkOverlap(empNo, accountType, accountNo, fromDate, toDate);
	}
	
	// 중복 체크(수정용)
	@ResponseBody
	@GetMapping("/checkOverlap/update")
	public boolean checkOverlap (@RequestParam String empNo, @RequestParam String accountType, @RequestParam String accountNo, 
			@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, @RequestParam long id) {
		return bankAccountService.checkOverlap(empNo, accountType, accountNo, fromDate, toDate, id);
	}
}
