package com.example.clsoftlab.controller.hr;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clsoftlab.dto.hr.EmployeeCardDetailDto;
import com.example.clsoftlab.service.common.CodeDetailService;
import com.example.clsoftlab.service.hr.EmployeeCardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/hr/employee-card")
@RequiredArgsConstructor
public class EmployeeCardController {

	private final EmployeeCardService employeeCardService;
	private final CodeDetailService codeDetailService;
	
	// 메인 페이지
	@GetMapping("")
	public String employCard (Model model) {
		
		model.addAttribute("deptCodeList", codeDetailService.findActiveCodesByGroupId("HR_DEPT"));
		model.addAttribute("rankCodeList", codeDetailService.findActiveCodesByGroupId("HR_RANK"));
		model.addAttribute("empStatusList", codeDetailService.findActiveCodesByGroupId("HR_STATUS"));
		model.addAttribute("dutyCodeList", codeDetailService.findActiveCodesByGroupId("HR_DUTY"));
		model.addAttribute("nationCodeList", codeDetailService.findActiveCodesByGroupId("HR_NATION"));
		model.addAttribute("maritalCodeList", codeDetailService.findActiveCodesByGroupId("HR_MARITAL"));
		model.addAttribute("militaryCodeList", codeDetailService.findActiveCodesByGroupId("HR_MILITARY"));
		model.addAttribute("maritalCodeList", codeDetailService.findActiveCodesByGroupId("HR_MARITAL"));
		model.addAttribute("militaryCodeList", codeDetailService.findActiveCodesByGroupId("HR_MILITARY"));
		model.addAttribute("relationCodeList", codeDetailService.findActiveCodesByGroupId("HR_REL_TYPE"));
		model.addAttribute("jobCodeList", codeDetailService.findActiveCodesByGroupId("HR_JOB_TYPE"));
		
		return "hr/employee-card/list";
	}
	
	// 사번으로 조회
	@GetMapping("/detail/{pernr}")
	public ResponseEntity<EmployeeCardDetailDto> findByPernr (@PathVariable String pernr) {
		return employeeCardService.findByPernr(pernr)
				.map(i -> ResponseEntity.ok(i))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
