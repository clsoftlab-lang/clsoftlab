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

import com.example.clsoftlab.dto.pay.EmployeeWithholdRateDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeWithholdRateRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.EmployeeWithholdRateService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/employee-withhold-rate")
public class EmployeeWithholdRateController {

	private final EmployeeWithholdRateService employeeWithholdRateService;
	private final EmployeeMasterService employeeMasterService;
	
	public EmployeeWithholdRateController(EmployeeWithholdRateService employeeWithholdRateService, EmployeeMasterService employeeMasterService) {
		this.employeeMasterService = employeeMasterService;
		this.employeeWithholdRateService = employeeWithholdRateService;
	}
	
	// 검색어로 page 조회
	@GetMapping("")
	public String searchWithholdRate (@RequestParam(required = false) List<String> empNo, @RequestParam(required = false) LocalDate fromDate,
			@RequestParam(required = false) LocalDate toDate, @RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		
		int size = 1000;
		
		Page<EmployeeWithholdRateDetailDto> employeeWithholdRatePage = employeeWithholdRateService.searchWithholdRate(empNo, fromDate, toDate, page, size);
		
		model.addAttribute("employeeWithholdRate", employeeWithholdRatePage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("employeeWithholdRatePage", employeeWithholdRatePage);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("searchEmployeeList", employeeWithholdRateService.getEmployeeList());
		
		return "pay/employee-withhold-rate/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewWithholdRate (@Valid @RequestBody EmployeeWithholdRateRequestDto dto) {
		employeeWithholdRateService.addNewWithholdRae(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateWithholdRate (@Valid @RequestBody EmployeeWithholdRateRequestDto dto) {
		employeeWithholdRateService.updateWithholdRate(dto);
		return ResponseEntity.ok().build();
	}
	
	// 디테일 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<EmployeeWithholdRateDetailDto> findById (@PathVariable Long id) {
		return employeeWithholdRateService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// 기간 중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String empNo, @RequestParam LocalDate fromDate,
			@RequestParam LocalDate toDate, @RequestParam(required = false) Long id) {
		boolean result = employeeWithholdRateService.checkOverlap(empNo, fromDate, toDate, id);
		
		return ResponseEntity.ok(result);
	}
}
