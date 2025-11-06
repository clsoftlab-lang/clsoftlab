package com.example.clsoftlab.controller.pay;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clsoftlab.dto.pay.EmployeeClubDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeClubRequestDto;
import com.example.clsoftlab.dto.pay.EmployeeClubSearchRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.ClubItemService;
import com.example.clsoftlab.service.pay.EmployeeClubService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/employee-club")
public class EmployeeClubController {

	private final EmployeeClubService employeeClubService;
	private final EmployeeMasterService employeeMasterService;
	private final ClubItemService clubItemService;
	
	public EmployeeClubController(EmployeeClubService employeeClubService, EmployeeMasterService employeeMasterService,
			ClubItemService clubItemService) {
		this.employeeClubService = employeeClubService;
		this.employeeMasterService = employeeMasterService;
		this.clubItemService = clubItemService;
	}
	
	//검색어로 전체 목록 조회
	@GetMapping("")
	public String getEmployeeClubList (@ModelAttribute EmployeeClubSearchRequestDto search, @RequestParam(required = false, defaultValue = "0") Integer page,
			Model model) {
		
		int size = 1000;
		
		Page<EmployeeClubDetailDto> employeeClubPage = employeeClubService.searchEmployeeClub(search, page, size);
		
		model.addAttribute("employeeClub", employeeClubPage.getContent());
		model.addAttribute("empNo", search.getEmpNo());
		model.addAttribute("clubCode", search.getClubCode());
		model.addAttribute("payYn", search.getPayYn());
		model.addAttribute("fromDate", search.getFromDate());
		model.addAttribute("toDate", search.getToDate());
		model.addAttribute("employeeClubPage", employeeClubPage);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("searchEmployeeList", employeeClubService.getEmployeeList());
		model.addAttribute("clubItemList", clubItemService.getClubItemList());
		model.addAttribute("searchClubItemList", employeeClubService.getClubItemList());
		
		return "pay/employee-club/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewEmployeeClub (@Valid @RequestBody EmployeeClubRequestDto dto) {
		employeeClubService.addNewEmployeeClub(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateEmployeeClub (@Valid @RequestBody EmployeeClubRequestDto dto) {
		employeeClubService.updateEmployeeClub(dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String empNo, @RequestParam Long clubId, 
			@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, @RequestParam(required = false) Long id) {
		boolean result = employeeClubService.checkOverlap(empNo, clubId, fromDate, toDate, id);
		return ResponseEntity.ok(result);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<EmployeeClubDetailDto> findById (@PathVariable long id) {
		return employeeClubService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
