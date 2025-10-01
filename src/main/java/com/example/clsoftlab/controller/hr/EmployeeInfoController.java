package com.example.clsoftlab.controller.hr;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clsoftlab.dto.hr.EmployeeInfoDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeInfoRequestDto;
import com.example.clsoftlab.service.hr.EmployeeInfoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/hr/employee-info")
public class EmployeeInfoController {

	private final EmployeeInfoService employeeInfoService;
	
	public EmployeeInfoController(EmployeeInfoService employeeInfoService) {
		this.employeeInfoService = employeeInfoService;
	}
	
	// 메인 페이지
	@GetMapping("")
	public String mainPage () {
		return "hr/employee-info/list";
	}
	
	// 항목 수정
	@PutMapping("/{pernr}")
	public ResponseEntity<Void> updateEmployeeInfo (@PathVariable String pernr, @Valid @RequestBody EmployeeInfoRequestDto dto) {
		employeeInfoService.updateEmployeeInfo(pernr, dto);
		return ResponseEntity.ok().build();
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{pernr}")
	public ResponseEntity<EmployeeInfoDetailDto> findById (@PathVariable String pernr) {
		return employeeInfoService.findById(pernr)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
