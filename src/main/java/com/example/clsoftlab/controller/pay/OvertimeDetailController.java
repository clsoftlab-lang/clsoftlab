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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.clsoftlab.dto.pay.OvertimeDetailDto;
import com.example.clsoftlab.dto.pay.OvertimeDetailRequestDto;
import com.example.clsoftlab.dto.pay.OvertimeDetailSearchDto;
import com.example.clsoftlab.service.pay.OvertimeDetailService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/overtime")
public class OvertimeDetailController {

	private final OvertimeDetailService overtimeDetailService;
	
	public OvertimeDetailController(OvertimeDetailService overtimeDetailService) {
		this.overtimeDetailService = overtimeDetailService;
	}
	
	// 검색어로 조회
	@GetMapping("")
	public String getOvertimeDetailList (@ModelAttribute OvertimeDetailSearchDto search, @RequestParam(required = false) Integer page, 
			Model model) {
		if (page == null) {
			page = 0;
		}
		int size = 1000;
		Page<OvertimeDetailDto> overtimeDetailPage = overtimeDetailService.searchOvertimeDetail(search, page, size);
		
		model.addAttribute("overtimeDetail", overtimeDetailPage.getContent());
		model.addAttribute("empNo", search.getEmpNo());
		model.addAttribute("date", search.getDate());
		model.addAttribute("type", search.getType());
		model.addAttribute("overtimeDetailPage", overtimeDetailPage);
		
		return "pay/overtime-detail/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewOvertimeDetail (@Valid @RequestBody OvertimeDetailRequestDto dto) {
		overtimeDetailService.addNewOvertimeDetail(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateOvertimeDetail (@PathVariable long id, @Valid @RequestBody OvertimeDetailRequestDto dto) {
		overtimeDetailService.updateOvertimeDetail(id, dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 체크
	@ResponseBody
	@GetMapping("/checkOverlap")
	public boolean checkOverlap (@RequestParam String empNo, @RequestParam LocalDate date, @RequestParam String type) {
		return overtimeDetailService.checkOverlap(empNo, date, type);
	}
	
	// 중복 체크 (수정용)
	@ResponseBody
	@GetMapping("/checkOverlap/update")
	public boolean checkOverlap (@RequestParam String empNo, @RequestParam LocalDate date, @RequestParam String type, @RequestParam long id) {
		return overtimeDetailService.checkOverlap(empNo, date, type, id);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<OvertimeDetailDto> findById (@PathVariable long id) {
		return overtimeDetailService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.ok().build());
	}
}
