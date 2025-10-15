package com.example.clsoftlab.controller.hr;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clsoftlab.dto.hr.EmployeePersonalRequestDto;
import com.example.clsoftlab.service.hr.EmployeePersonalService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/hr/employee-personal")
public class EmployeePersonalController {

	private final EmployeePersonalService employeePersonalService;
	
	public EmployeePersonalController(EmployeePersonalService employeePersonalService) {
		this.employeePersonalService = employeePersonalService;
	}
	
	// 메인 페이지
	@GetMapping("")
	public String employeePersonal ( ) {
		return "hr/employee-personal/list";
	}
	
	// 새 정보 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewPersonal (@Valid @RequestBody EmployeePersonalRequestDto dto) {
		employeePersonalService.addNewPersonal(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 정보 수정
	@PutMapping("")
	public ResponseEntity<Void> updatePersonal (@Valid @RequestBody EmployeePersonalRequestDto dto) {
		employeePersonalService.updatePersonal(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 정보 삭제
	@DeleteMapping("/{pernr}")
	public ResponseEntity<Void> deletePersonal (@PathVariable String pernr) {
		employeePersonalService.deletePersonal(pernr);
		return ResponseEntity.ok().build();
	}
	
	// 사번 중복 검사
	@GetMapping("/checkOverlap/{pernr}")
	public ResponseEntity<Boolean> checkOverlap (@PathVariable String pernr) {
		boolean check = employeePersonalService.checkOverlap(pernr);
		return ResponseEntity.ok(check);
	}
}
