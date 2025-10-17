package com.example.clsoftlab.controller.hr;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clsoftlab.dto.hr.EmployeeCardDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeCardRequestDto;
import com.example.clsoftlab.service.hr.EmployeeCardService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/hr/employee-card")
public class EmployeeCardController {

	private final EmployeeCardService employeeCardService;
	
	public EmployeeCardController(EmployeeCardService employeeCardService) {
		this.employeeCardService = employeeCardService;
	}
	
	// 메인 페이지
	@GetMapping("")
	public String employCard () {
		return "hr/employee-card/list";
	}
	
	// 인사카드 수정, 저장
	@PutMapping("")
	public ResponseEntity<Void> saveCard (@Valid @RequestBody EmployeeCardRequestDto dto) {
		employeeCardService.saveCard(dto);
		return ResponseEntity.ok().build();
	}
	
	// 사번으로 조회
	@GetMapping("/detail/{pernr}")
	public ResponseEntity<EmployeeCardDetailDto> findByPernr (@PathVariable String pernr) {
		return employeeCardService.findByPernr(pernr)
				.map(i -> ResponseEntity.ok(i))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
