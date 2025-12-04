package com.example.clsoftlab.controller.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.clsoftlab.dto.common.UserAccountResponseDto;
import com.example.clsoftlab.dto.hr.EmployeeFamilyDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeFamilyRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.EmployeeFamilyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/hr/employee-family")
@RequiredArgsConstructor
public class EmployeeFamilyController {

	private final EmployeeFamilyService employeeFamilyService;
	private final EmployeeMasterService employeeMasterService;
	
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
	
	//카드 페이지 심플list 조회
	@GetMapping("/simple/{pernr}")
	public ResponseEntity<?> findById (@PathVariable String pernr, @SessionAttribute(name = "LOGIN_USER", required = false) UserAccountResponseDto sessionUser) {
		
		if (sessionUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
	    }

	    boolean isMyData = sessionUser.getUserId().equals(pernr);
	    
	    boolean isAdminOrHr = "ADMIN".equals(sessionUser.getSysRole()) 
	                       || "HR_MNG".equals(sessionUser.getSysRole());

	    if (!isMyData && !isAdminOrHr) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("타인의 정보를 조회할 권한이 없습니다.");
	    }
		
		return ResponseEntity.ok(employeeFamilyService.getEmployeeSimpleFamilyList(pernr));
				
	}
}
