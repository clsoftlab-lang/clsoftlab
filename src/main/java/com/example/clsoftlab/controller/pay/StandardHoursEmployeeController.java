package com.example.clsoftlab.controller.pay;

import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.clsoftlab.dto.pay.StandardHoursEmployeeDetailDto;
import com.example.clsoftlab.dto.pay.StandardHoursEmployeeRequestDto;
import com.example.clsoftlab.service.pay.StandardHoursEmployeeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/standard-hours-employee")
public class StandardHoursEmployeeController {

	private final StandardHoursEmployeeService standardHoursEmployeeService;
	
	public StandardHoursEmployeeController(StandardHoursEmployeeService standardHoursEmployeeService) {
		this.standardHoursEmployeeService = standardHoursEmployeeService;
	}
	
	// 전체 목록 조회
	@GetMapping("")
	public String getStandardHoursEmployeeList (@RequestParam(defaultValue = "")String calYm, @RequestParam(defaultValue = "")String empNo,
			@RequestParam(required = false) Integer page, Model model) {
		
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<StandardHoursEmployeeDetailDto> standardHoursEmployeePage = standardHoursEmployeeService.searchStandardHoursEmployee(calYm, empNo, page, size);
		
		model.addAttribute("standardHoursEmployee", standardHoursEmployeePage.getContent());
		model.addAttribute("calYm", calYm);
		model.addAttribute("empNo", empNo);
		model.addAttribute("standardHoursEmployeePage", standardHoursEmployeePage);
		
		return "pay/standard-hours-employee/list";
	}
	
	// 새 근로시간 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewStandardHoursEmployee (@Valid @RequestBody StandardHoursEmployeeRequestDto dto) {
		standardHoursEmployeeService.addNewStandardHoursEmployee(dto);
		return ResponseEntity.ok().build();
	}
	
	// 특정 근로시간 수정
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateStandardHoursEmployee (@PathVariable long id, @Valid @RequestBody StandardHoursEmployeeRequestDto dto) {
		standardHoursEmployeeService.updateStandardHoursEmployee(id, dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 체크
	@ResponseBody
	@GetMapping("/checkOverlap")
	public long checkOverlappingStandardHoursEmployee (@RequestParam String calYm, @RequestParam String empNo) {
		
		return standardHoursEmployeeService.checkOverlappingStandardHoursEmployee(calYm, empNo);
	}
	
	// 디테일 정보 
	@GetMapping("detail/{id}")
	public ResponseEntity<StandardHoursEmployeeDetailDto> findById (@PathVariable long id) {
		return standardHoursEmployeeService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
