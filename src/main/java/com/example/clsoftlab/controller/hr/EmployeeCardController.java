package com.example.clsoftlab.controller.hr;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clsoftlab.dto.hr.EmployeeCardDetailDto;
import com.example.clsoftlab.service.common.CodeDetailService;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.hr.EmployeeCardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/hr/employee-card")
@RequiredArgsConstructor
public class EmployeeCardController {

	private final EmployeeCardService employeeCardService;
	private final EmployeeMasterService employeeMasterService;
	private final CodeDetailService codeDetailService;
	
	// 메인 페이지
	@GetMapping("")
	public String employeeCard (Model model) {
		
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("deptCodeList", codeDetailService.findActiveCodesByGroupId("HR_DEPT"));
		model.addAttribute("rankCodeList", codeDetailService.findActiveCodesByGroupId("HR_RANK"));
		model.addAttribute("empStatusList", codeDetailService.findActiveCodesByGroupId("HR_STATUS"));
		model.addAttribute("dutyCodeList", codeDetailService.findActiveCodesByGroupId("HR_DUTY"));
		model.addAttribute("nationCodeList", codeDetailService.findActiveCodesByGroupId("HR_NATION"));
		model.addAttribute("maritalCodeList", codeDetailService.findActiveCodesByGroupId("HR_MARITAL"));
		model.addAttribute("militaryCodeList", codeDetailService.findActiveCodesByGroupId("HR_MILITARY"));
		model.addAttribute("relationCodeList", codeDetailService.findActiveCodesByGroupId("HR_REL_TYPE"));
		model.addAttribute("jobCodeList", codeDetailService.findActiveCodesByGroupId("HR_JOB_TYPE"));
		model.addAttribute("certCodeList", codeDetailService.findActiveCodesByGroupId("HR_CERT_CD"));
		model.addAttribute("certRankList", codeDetailService.findActiveCodesByGroupId("HR_CERT_RANK"));
		model.addAttribute("langCodeList", codeDetailService.findActiveCodesByGroupId("HR_LANG"));
		model.addAttribute("langTestCodeList", codeDetailService.findActiveCodesByGroupId("HR_LANG_TEST"));
		model.addAttribute("langLvlCodeList", codeDetailService.findActiveCodesByGroupId("HR_LANG_LVL"));
		model.addAttribute("careerTypeCodeList", codeDetailService.findActiveCodesByGroupId("HR_CAREER_TYPE"));
		model.addAttribute("rewardTypeCodeList", codeDetailService.findActiveCodesByGroupId("HR_REWARD_TYPE"));
		model.addAttribute("eduTypeCodeList", codeDetailService.findActiveCodesByGroupId("HR_EDU_TYPE"));
		model.addAttribute("evalTypeCodeList", codeDetailService.findActiveCodesByGroupId("EV_TYPE"));
		model.addAttribute("evalGradeCodeList", codeDetailService.findActiveCodesByGroupId("EV_GRADE"));
		model.addAttribute("schoolTypeCodeList", codeDetailService.findActiveCodesByGroupId("HR_SCH_TYPE"));
		model.addAttribute("graStatCodeList", codeDetailService.findActiveCodesByGroupId("HR_GRA_STAT"));
		
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
