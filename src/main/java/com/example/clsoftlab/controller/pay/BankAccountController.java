package com.example.clsoftlab.controller.pay;

import java.time.LocalDate;
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

import com.example.clsoftlab.dto.pay.BankAccountDetailDto;
import com.example.clsoftlab.dto.pay.BankAccountRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.BankAccountService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/bank-account")
public class BankAccountController {
	
	private final BankAccountService bankAccountService;
	private final EmployeeMasterService employeeMasterService;
	
	public BankAccountController(BankAccountService bankAccountService, EmployeeMasterService employeeMasterService) {
		this.bankAccountService = bankAccountService;
		this.employeeMasterService = employeeMasterService;
	}

	// 전체 목록 조회
	@GetMapping("")
	public String getBankAccountList (@RequestParam(required = false) List<String> empNo, @RequestParam(required = false) List<String> accountType,
			@RequestParam(required = false) String useYn, @RequestParam(required = false, defaultValue = "0") Integer page , Model model) {
		
		int size = 1000;
		
		Page<BankAccountDetailDto> bankAccountPage = bankAccountService.searchBankAccount(empNo, accountType, useYn, page, size);
		
		model.addAttribute("bankAccount", bankAccountPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("accountType", accountType);
		model.addAttribute("useYn", useYn);
		model.addAttribute("bankAccountPage", bankAccountPage);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("searchEmployeeList", bankAccountService.getEmployeeList());
		
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
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String empNo, @RequestParam String accountType, @RequestParam String accountNo, 
			@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, @RequestParam(required = false) Long id) {
		boolean result = bankAccountService.checkOverlap(empNo, accountType, accountNo, fromDate, toDate, id);
	
		return ResponseEntity.ok(result);
	}
	
	// 세부 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<BankAccountDetailDto> findById (@PathVariable long id) {
		return bankAccountService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
