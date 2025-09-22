package com.example.clsoftlab.controller.pay;

import java.time.LocalDate;

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

import com.example.clsoftlab.dto.pay.StepBaseDetailDto;
import com.example.clsoftlab.dto.pay.StepBaseRequestDto;
import com.example.clsoftlab.service.pay.StepBaseService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/step-base")
public class StepBaseController {
	
	private final StepBaseService stepBaseService;
	
	public StepBaseController(StepBaseService stepBaseService) {
		this.stepBaseService = stepBaseService;
	}

	// 검색어로 목록 조회
	@GetMapping("")
	public String getStepBaseList (@RequestParam(defaultValue = "") String gradeCode, @RequestParam(required = false) Integer stepNo,
			@RequestParam(defaultValue = "") String useYn, @RequestParam(required = false) Integer page,
			Model model) {
		
		if (page == null) {
			page = 0;
		}
		int size = 1000;
		
		Page<StepBaseDetailDto> stepBasePage = stepBaseService.searchStepBase(gradeCode, stepNo, useYn, page, size);
		
		model.addAttribute("stepBase", stepBasePage.getContent());
		model.addAttribute("gradeCode", gradeCode);
		model.addAttribute("stepNo", stepNo);
		model.addAttribute("useYn", useYn);
		model.addAttribute("stepBasePage", stepBasePage);
		
		return "pay/step-base/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewStepBase (@Valid @RequestBody StepBaseRequestDto dto) {
		stepBaseService.addNewStepBase(dto);
		
		return ResponseEntity.ok().build();
	}
	
	// 특정 항목 수정
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateStepBase (@PathVariable long id,@Valid @RequestBody StepBaseRequestDto dto) {
		stepBaseService.updateStepBase(id, dto);
		
		return ResponseEntity.ok().build();
	}
	
	// 중복 확인
	@ResponseBody
	@GetMapping("/checkOverlap")
	public boolean checkOverlap (@RequestParam String gradeCode, @RequestParam String stepNo, @RequestParam LocalDate fromDate,
			@RequestParam LocalDate toDate) {
		return stepBaseService.checkOverlapping(gradeCode, stepNo, fromDate, toDate);
	}
	
	// 중복 확인(수정용)
	@ResponseBody
	@GetMapping("/checkOverlap/update")
	public boolean checkOverlapForUpdate (@RequestParam String gradeCode, @RequestParam String stepNo, @RequestParam LocalDate fromDate,
			@RequestParam LocalDate toDate, @RequestParam long id) {
		return stepBaseService.checkOverlapping(gradeCode, stepNo, fromDate, toDate, id);
	}
	
	// 세부 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<StepBaseDetailDto> findById (@PathVariable long id) {
		return stepBaseService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
