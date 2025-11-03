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

import com.example.clsoftlab.dto.pay.EmployeeStepDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeStepRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.EmployeeStepService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/employee-step")
public class EmployeeStepController {

	private final EmployeeStepService employeeStepService;
	private final EmployeeMasterService employeeMasterService;
	
	public EmployeeStepController(EmployeeStepService employeeStepService, EmployeeMasterService employeeMasterService) {
		this.employeeStepService = employeeStepService;
		this.employeeMasterService = employeeMasterService;
	}
	
	// 검색어로 page 조회
	@GetMapping("")
	public String searchEmployeeStep (@RequestParam(required = false) List<String> pernr, @RequestParam(required = false) List<String> gradeCode,
			@RequestParam(required = false) Integer fromDate, @RequestParam(required = false) Integer toDate, 
			@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		
		int size = 1000;
		
		Page<EmployeeStepDetailDto> employeeStepPage = employeeStepService.searchEmployeeStep(pernr, gradeCode, fromDate, toDate, page, size);
		
		model.addAttribute("employeeStep", employeeStepPage.getContent());
		model.addAttribute("pernr", pernr);
		model.addAttribute("gradeCode", gradeCode);
		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("searchEmployeeList", employeeStepService.getEmployeeList());
		model.addAttribute("gradeCodeList", employeeStepService.getGradeCodeList());
		
		return "pay/employee-step/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewStep (@Valid @RequestBody EmployeeStepRequestDto dto) {
		employeeStepService.addNewStep(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateStep (@Valid @RequestBody EmployeeStepRequestDto dto) {
		employeeStepService.updateStep(dto);
		return ResponseEntity.ok().build();
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<EmployeeStepDetailDto> findById (@PathVariable Long id) {
		return employeeStepService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// 기간 중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String pernr, @RequestParam LocalDate fromDate, 
			@RequestParam LocalDate toDate, @RequestParam(required = false) Long id) {
		
		boolean result = employeeStepService.checkOverlap(pernr, fromDate, toDate, id);
		
		return ResponseEntity.ok(result);
	}
}
