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

import com.example.clsoftlab.dto.pay.StandardHoursDetailDto;
import com.example.clsoftlab.dto.pay.StandardHoursRequestDto;
import com.example.clsoftlab.service.pay.StandardHoursService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/standard-hours")
public class StandardHoursController {

	private final StandardHoursService standardHoursService;
	
	public StandardHoursController(StandardHoursService standardHoursService) {
		this.standardHoursService = standardHoursService;
	}
	
	// 검색으로 전체 목록 조회
	@GetMapping("")
	public String getStandardHoursList (@RequestParam(defaultValue = "") String calYm, @RequestParam(defaultValue = "") String jobGroup,
			@RequestParam(required = false) Integer page, Model model) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<StandardHoursDetailDto> standardHoursPage = standardHoursService.searchStandardHours(calYm, jobGroup, page, size);
		
		model.addAttribute("standardHours", standardHoursPage.getContent());
		model.addAttribute("calYm", calYm);
		model.addAttribute("jobGroup", jobGroup);
		model.addAttribute("standardHoursPage", standardHoursPage);
		return "pay/standard-hours/list";
	}
	
	// 새 근로시간 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewStandardHours (@Valid @RequestBody StandardHoursRequestDto dto) {
		standardHoursService.addNewStandardHours(dto);
		return ResponseEntity.ok().build();
	}
	
	// 특정 근로시간 수정
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateStandardHours (@PathVariable long id, @Valid @RequestBody StandardHoursRequestDto dto) {
		standardHoursService.updateStandardHours(id, dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사
	@ResponseBody
	@GetMapping("/checkOverlap")
	public long checkOverlappingStandardHours (@RequestParam String calYm, @RequestParam(required = false) String jobGroup) {

		return standardHoursService.checkOverlappingStandardHours(calYm, jobGroup);
	}
	
	// 디테일 정보
	@GetMapping("/detail/{id}")
	public ResponseEntity<StandardHoursDetailDto> findById (@PathVariable long id) {
		return standardHoursService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
