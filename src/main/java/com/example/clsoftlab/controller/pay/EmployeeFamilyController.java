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

import com.example.clsoftlab.dto.pay.EmployeeFamilyDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeFamilyRequestDto;
import com.example.clsoftlab.service.pay.EmployeeFamilyService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/employee-family")
public class EmployeeFamilyController {

	private final EmployeeFamilyService employeeFamilyService;
	
	public EmployeeFamilyController(EmployeeFamilyService employeeFamilyService) {
		this.employeeFamilyService = employeeFamilyService;
	}
	
	// 검색어로 목록 조회
	@GetMapping("")
	public String getEmployeeFamilyList (@RequestParam(defaultValue = "")String empNo, @RequestParam(defaultValue = "")String familyName,
			@RequestParam(defaultValue = "") String familyType, @RequestParam(required = false) Integer page, Model model) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<EmployeeFamilyDetailDto> employeeFamilyPage = employeeFamilyService.searchEmployeeFamily(empNo, familyName, familyType, page, size);
		
		model.addAttribute("employeeFamily", employeeFamilyPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("familyName", familyName);
		model.addAttribute("familyType", familyType);
		model.addAttribute("employeeFamilyPage", employeeFamilyPage);
		
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
	@ResponseBody
	@GetMapping("/checkOverlap")
	public boolean checkOverlap (@RequestParam String empNo, @RequestParam Integer familySeq) {
		return employeeFamilyService.checkOverlap(empNo, familySeq);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<EmployeeFamilyDetailDto> findById (@PathVariable long id) {
		return employeeFamilyService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
