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

import com.example.clsoftlab.dto.pay.EmployeeFamilyDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeFamilyRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.EmployeeFamilyService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/employee-family")
public class EmployeeFamilyController {

	private final EmployeeFamilyService employeeFamilyService;
	private final EmployeeMasterService employeeMasterService;
	
	public EmployeeFamilyController(EmployeeFamilyService employeeFamilyService, EmployeeMasterService employeeMasterService) {
		this.employeeFamilyService = employeeFamilyService;
		this.employeeMasterService = employeeMasterService;
	}
	
	// 검색어로 목록 조회
	@GetMapping("")
	public String getEmployeeFamilyList (@RequestParam(required = false)List<String> empNo, @RequestParam(required = false)String familyName,
			@RequestParam(required = false) List<String> familyType, @RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		int size = 1000;
		
		Page<EmployeeFamilyDetailDto> employeeFamilyPage = employeeFamilyService.searchEmployeeFamily(empNo, familyName, familyType, page, size);
		
		model.addAttribute("employeeFamily", employeeFamilyPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("familyName", familyName);
		model.addAttribute("familyType", familyType);
		model.addAttribute("employeeFamilyPage", employeeFamilyPage);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("searchEmployeeList", employeeFamilyService.getEmployeeList());
		
		return "pay/employee-family/list";
	}
	
	// 새 사원 가족 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewEmployeeFamily (@Valid @RequestBody EmployeeFamilyRequestDto dto) {
		employeeFamilyService.addNewEmployeeFamily(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 가족 정보 수정
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateEmployeeFamily (@PathVariable long id, @Valid @RequestBody EmployeeFamilyRequestDto dto) {
		employeeFamilyService.updateEmployeeFamily(id, dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 체크
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String empNo, @RequestParam Integer familySeq) {
		boolean result = employeeFamilyService.checkOverlap(empNo, familySeq);
		return ResponseEntity.ok(result);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<EmployeeFamilyDetailDto> findById (@PathVariable long id) {
		return employeeFamilyService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
