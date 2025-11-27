package com.example.clsoftlab.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.service.common.CodeDetailService;
import com.example.clsoftlab.service.common.EmployeeMasterService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/employee-manage")
@RequiredArgsConstructor
public class EmployeeManageController {

	private final EmployeeMasterService employeeMasterService;
	private final CodeDetailService codeDetailService;
	
	// 검색어로 page 조회
	@GetMapping("")
	public String searchEmployee (@RequestParam(required = false) List<String> pernr, @RequestParam(required = false) List<String> dutyCode,
			@RequestParam(required = false) List<String> rankCode, @RequestParam(required = false) List<String> empStatus, 
			@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		int size = 1000;
		
		Page<EmployeeMasterDto> employeePage = employeeMasterService.searchEmployee(pernr, dutyCode, rankCode, empStatus, page, size);
		
		model.addAttribute("employeeMaster", employeePage.getContent());
		model.addAttribute("pernr", pernr);
		model.addAttribute("dutyCode", dutyCode);
		model.addAttribute("rankCode", rankCode);
		model.addAttribute("empStatus", empStatus);
		model.addAttribute("employeeMasterPage", employeePage);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("dutyCodeList", codeDetailService.findActiveCodesByGroupId("HR_DUTY"));
		model.addAttribute("rankCodeList", codeDetailService.findActiveCodesByGroupId("HR_RANK"));
		model.addAttribute("empStatusList", codeDetailService.findActiveCodesByGroupId("HR_STATUS"));
		
		return "admin/employee-manage/list";
	}
}
