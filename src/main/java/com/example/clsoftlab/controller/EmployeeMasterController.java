package com.example.clsoftlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;

@Controller
@RequestMapping("/employee-master")
public class EmployeeMasterController {

	private final EmployeeMasterService employeeMasterService;
	
	public EmployeeMasterController(EmployeeMasterService employeeMasterService) {
		this.employeeMasterService = employeeMasterService;
	}
	
	// 특정 사번으로 상세 정보 조회
	@GetMapping("/detail/{pernr}")
	public ResponseEntity<EmployeeMasterDto> findByPernr (@PathVariable String pernr) {
		return employeeMasterService.findByPernr(pernr)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
