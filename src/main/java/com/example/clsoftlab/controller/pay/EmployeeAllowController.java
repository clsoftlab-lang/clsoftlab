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

import com.example.clsoftlab.dto.pay.EmployeeAllowDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeAllowRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.EmployeeAllowService;
import com.example.clsoftlab.service.pay.PayItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/employee-allow")
public class EmployeeAllowController {

	private final EmployeeAllowService employeeAllowService;
	private final EmployeeMasterService employeeMasterService;
	private final PayItemService payItemService;
	
	public EmployeeAllowController(EmployeeAllowService employeeAllowService, EmployeeMasterService employeeMasterService,
			PayItemService payItemService) {
		this.employeeAllowService = employeeAllowService;
		this.employeeMasterService = employeeMasterService;
		this.payItemService = payItemService;
	}
	
	// 검색어로 page 조회
	@GetMapping("")
	public String getAllowList (@RequestParam(required = false) List<String> empNo, @RequestParam(required = false) List<String> itemCode, 
			@RequestParam(required = false) String useYn, @RequestParam(required = false) Integer page, Model model) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<EmployeeAllowDetailDto> allowPage = employeeAllowService.searchAllow(empNo, itemCode, useYn, page, size);
		
		model.addAttribute("employeeAllow", allowPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("useYn", useYn);
		model.addAttribute("allowPage", allowPage);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("searchEmployeeList", employeeAllowService.getEmployeeList());
		model.addAttribute("payItemList", payItemService.findAllForSearch());
		model.addAttribute("searchPayItemList", employeeAllowService.getPayItemList());
		
		return "pay/employee-allow/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewAllow (@Valid @RequestBody EmployeeAllowRequestDto dto) {
		employeeAllowService.addNewAllow(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateAllow (@PathVariable Long id, @RequestBody EmployeeAllowRequestDto dto) {
		employeeAllowService.updateAllow(id, dto);
		return ResponseEntity.ok().build();
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<EmployeeAllowDetailDto> findById (@PathVariable Long id) {
		return employeeAllowService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// 중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOvelrap (@RequestParam String itemCode, @RequestParam String empNo, 
			@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, @RequestParam(required = false) Long id) {
		
		boolean result;
		
		if (id == null) {
			result = employeeAllowService.checkOverlap(empNo, itemCode, fromDate, toDate);
			
		} else {
			result = employeeAllowService.checkOverlap(empNo, itemCode, fromDate, toDate, id);
		}
		return ResponseEntity.ok(result);
	}
	
}
