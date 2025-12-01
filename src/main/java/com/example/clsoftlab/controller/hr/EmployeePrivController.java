package com.example.clsoftlab.controller.hr;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.clsoftlab.dto.common.UserAccountResponseDto;
import com.example.clsoftlab.dto.hr.EmployeePrivLogDetailDto;
import com.example.clsoftlab.dto.hr.EmployeePrivRequestDto;
import com.example.clsoftlab.service.hr.EmployeePrivService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/hr/employee-priv")
public class EmployeePrivController {

	private final EmployeePrivService employeePrivService;
	
	public EmployeePrivController(EmployeePrivService employeePrivService) {
		this.employeePrivService = employeePrivService;
	}
	
	// 메인 화면
	@GetMapping("")
	public String mainPage () {
		
		return "hr/employee-priv/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewEmployeePriv (@Valid @RequestBody EmployeePrivRequestDto dto) {
		employeePrivService.addNewEmployeePriv(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateEmployeePriv (@Valid @RequestBody EmployeePrivRequestDto dto) {
		employeePrivService.updateEmployeePriv(dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사
	@ResponseBody
	@GetMapping("/checkOverlap")
	public boolean checkOverlap (@RequestParam String pernr) {
		return employeePrivService.checkOverlap(pernr);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{pernr}")
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
		
		return employeePrivService.findById(pernr)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// 이력 조회
	@ResponseBody
	@GetMapping("/log/{pernr}")
	public Page<EmployeePrivLogDetailDto> getLogList (@PathVariable String pernr, @RequestParam(required = false) Integer page) {
		if (page == null) {
			page = 0;
		}
		
		int size= 1000;
		return employeePrivService.findByPernr(pernr, page, size);
	}
}
