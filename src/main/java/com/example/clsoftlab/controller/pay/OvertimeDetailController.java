package com.example.clsoftlab.controller.pay;

import java.time.LocalDate;
import java.util.List;

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

import com.example.clsoftlab.dto.pay.OvertimeDetailDto;
import com.example.clsoftlab.dto.pay.OvertimeDetailRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.OvertimeDetailService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/overtime")
public class OvertimeDetailController {

	private final OvertimeDetailService overtimeDetailService;
	private final EmployeeMasterService employeeMasterService;
	
	public OvertimeDetailController(OvertimeDetailService overtimeDetailService, EmployeeMasterService employeeMasterService) {
		this.overtimeDetailService = overtimeDetailService;
		this.employeeMasterService = employeeMasterService;
	}
	
	// 검색어로 조회
	@GetMapping("")
	public String getOvertimeDetailList (@RequestParam(required = false) List<String> empNo, @RequestParam(required = false) LocalDate date,
			@RequestParam(required = false) List<String> type ,@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		int size = 1000;
		Page<OvertimeDetailDto> overtimeDetailPage = overtimeDetailService.searchOvertimeDetail(empNo, date, type, page, size);
		
		model.addAttribute("overtimeDetail", overtimeDetailPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("date", date);
		model.addAttribute("type", type);
		model.addAttribute("overtimeDetailPage", overtimeDetailPage);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("searchEmployeeList", overtimeDetailService.getEmployeeList());
		
		return "pay/overtime-detail/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewOvertimeDetail (@Valid @RequestBody OvertimeDetailRequestDto dto) {
		overtimeDetailService.addNewOvertimeDetail(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateOvertimeDetail (@Valid @RequestBody OvertimeDetailRequestDto dto) {
		overtimeDetailService.updateOvertimeDetail(dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 체크
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String empNo, @RequestParam LocalDate date, @RequestParam String type, @RequestParam(required = false) Long id) {
		boolean result = overtimeDetailService.checkOverlap(empNo, date, type, id);
		return ResponseEntity.ok(result);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<OvertimeDetailDto> findById (@PathVariable long id) {
		return overtimeDetailService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.ok().build());
	}
}
