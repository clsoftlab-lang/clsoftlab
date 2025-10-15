package com.example.clsoftlab.controller.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clsoftlab.dto.hr.EmployeeEduHistoryDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeEduHistoryRequestDto;
import com.example.clsoftlab.service.hr.EmployeeEduHistoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/hr/employee-edu-history")
public class EmployeeEduHistoryController {

	private final EmployeeEduHistoryService employeeEduHistoryService;
	
	public EmployeeEduHistoryController(EmployeeEduHistoryService employeeEduHistoryService) {
		this.employeeEduHistoryService = employeeEduHistoryService;
	}
	
	// 메인 페이지
	@GetMapping("")
	public String employeeEduHistory ( ) {
		return "hr/employee-edu-history/list";
	}
	
	// 학력 리스트 저장
	@PutMapping("/{pernr}")
	public ResponseEntity<?> saveHistoryList (@PathVariable String pernr, @Valid @RequestBody List<EmployeeEduHistoryRequestDto> dtoList) {
		try {
			employeeEduHistoryService.saveHistoryList(pernr, dtoList);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 사번으로 학력 page 조회
	@GetMapping("/detail/{pernr}")
	public ResponseEntity<Page<EmployeeEduHistoryDetailDto>> findByPernr (@PathVariable String pernr, @RequestParam(required = false) Integer page) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<EmployeeEduHistoryDetailDto> historyPage = employeeEduHistoryService.findByPernr(pernr, page, size);
		
		return ResponseEntity.ok(historyPage);
	}
}