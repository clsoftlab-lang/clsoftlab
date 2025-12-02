package com.example.clsoftlab.controller.hr;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.clsoftlab.dto.common.UserAccountResponseDto;
import com.example.clsoftlab.service.hr.AppointHistService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/hr/employee-appoint")
@RequiredArgsConstructor
public class EmployeeAppointController {

	private final AppointHistService appointHistService;
	
	//발령 이력 조회
	@GetMapping("/history/{pernr}")
	public ResponseEntity<?> getEmployeeHistory (@PathVariable String pernr, @SessionAttribute(name = "LOGIN_USER", required = false) UserAccountResponseDto sessionUser) {
		
		if (sessionUser == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
	    }

	    boolean isMyData = sessionUser.getUserId().equals(pernr);
	    
	    boolean isAdminOrHr = "ADMIN".equals(sessionUser.getSysRole()) 
	                       || "HR_MNG".equals(sessionUser.getSysRole());

	    if (!isMyData && !isAdminOrHr) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("타인의 정보를 조회할 권한이 없습니다.");
	    }
	    
	    return ResponseEntity.ok(appointHistService.findByPernr(pernr));
	}
}
