package com.example.clsoftlab.controller.pay;

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

import com.example.clsoftlab.dto.pay.ClubPayDetailDto;
import com.example.clsoftlab.dto.pay.ClubPayDetailRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.ClubItemService;
import com.example.clsoftlab.service.pay.ClubPayDetailService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/club-pay-detail")
public class ClubPayDetailController {

	private final ClubPayDetailService clubPayDetailService;
	private final EmployeeMasterService employeeMasterService;
	private final ClubItemService clubItemService;
	
	public ClubPayDetailController(ClubPayDetailService clubPayDetailService, EmployeeMasterService employeeMasterService,
			ClubItemService clubItemService) {
		this.clubPayDetailService = clubPayDetailService;
		this.employeeMasterService = employeeMasterService;
		this.clubItemService = clubItemService;
	}
	
	// 검색어로 page 조회
	@GetMapping("")
	public String searchClubPayDetail (@RequestParam(required = false) List<String> empNo, @RequestParam(required = false) String payYm,
			@RequestParam(required = false) List<String> clubCode, @RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		
		int size= 1000;
		
		Page<ClubPayDetailDto> clubPayDetailPage = clubPayDetailService.searchClubPayDetail(empNo, payYm, clubCode, page, size);
		
		model.addAttribute("clubPayDetail", clubPayDetailPage.getContent());
		model.addAttribute("empNo", empNo);
		model.addAttribute("payYm", payYm);
		model.addAttribute("clubCode", clubCode);
		model.addAttribute("clubPayDetailPage", clubPayDetailPage);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("searchEmployeeList", clubPayDetailService.getEmployeeList());
		model.addAttribute("clubItemList", clubItemService.getClubItemList());
		model.addAttribute("searchClubItemList", clubPayDetailService.getClubItemList());
		
		return "pay/club-pay-detail/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewDetail (@Valid @RequestBody ClubPayDetailRequestDto dto) {
		clubPayDetailService.addNewDetail(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateDetail (@Valid @RequestBody ClubPayDetailRequestDto dto) {
		clubPayDetailService.updateDetail(dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사
	@GetMapping("/checkOverlap") 
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String empNo, @RequestParam String payYm, @RequestParam Long clubId) {
		boolean result = clubPayDetailService.checkOverlap(empNo, payYm, clubId);
		return ResponseEntity.ok(result);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<ClubPayDetailDto> findById (@PathVariable Long id) {
		return clubPayDetailService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
