package com.example.clsoftlab.controller.pay;

import java.time.LocalDate;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.clsoftlab.dto.pay.ClubItemDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeClubDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeClubRequestDto;
import com.example.clsoftlab.dto.pay.EmployeeClubSearchRequestDto;
import com.example.clsoftlab.service.pay.ClubItemService;
import com.example.clsoftlab.service.pay.EmployeeClubService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/employee-club")
public class EmployeeClubController {

	private final EmployeeClubService employeeClubService;
	private final ClubItemService clubItemService;
	
	public EmployeeClubController(EmployeeClubService employeeClubService, ClubItemService clubItemService) {
		this.employeeClubService = employeeClubService;
		this.clubItemService = clubItemService;
	}
	
	//검색어로 전체 목록 조회
	@GetMapping("")
	public String getEmployeeClubList (@ModelAttribute EmployeeClubSearchRequestDto search, @RequestParam(required = false) Integer page,
			Model model) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<EmployeeClubDetailDto> employeeClubPage = employeeClubService.searchEmployeeClub(search, page, size);
		
		if (search.getClubCode() == null) {
			search.setClubCode("");
		}
		
		Optional<ClubItemDetailDto> clubItem = clubItemService.findById(search.getClubCode());
		
		model.addAttribute("employeeClub", employeeClubPage.getContent());
		model.addAttribute("empNo", search.getEmpNo());
		model.addAttribute("clubCode", search.getClubCode());
		model.addAttribute("fromDate", search.getFromDate());
		model.addAttribute("toDate", search.getToDate());
		model.addAttribute("employeeClubPage", employeeClubPage);
		
		if (clubItem.isPresent()) {
			model.addAttribute("clubName", clubItem.get().getClubName());
		}
		
		return "pay/employee-club/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewEmployeeClub (@Valid @RequestBody EmployeeClubRequestDto dto) {
		employeeClubService.addNewEmployeeClub(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateEmployeeClub (@PathVariable long id, @Valid @RequestBody EmployeeClubRequestDto dto) {
		employeeClubService.updateEmployeeClub(id, dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사
	@ResponseBody
	@GetMapping("/checkOverlap")
	public boolean checkOverlap (@RequestParam String empNo, @RequestParam String clubCode, 
			@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate) {
		return employeeClubService.checkOverlap(empNo, clubCode, fromDate, toDate);
	}
	
	// 중복 검사 (수정용)
	@ResponseBody
	@GetMapping("/checkOverlap/update")
	public boolean checkOverlapForUpdate (@RequestParam String empNo, @RequestParam String clubCode, 
			@RequestParam LocalDate fromDate, @RequestParam LocalDate toDate, @RequestParam long id) {
		return employeeClubService.checkOverlap(empNo, clubCode, fromDate, toDate, id);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<EmployeeClubDetailDto> findById (@PathVariable long id) {
		return employeeClubService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
