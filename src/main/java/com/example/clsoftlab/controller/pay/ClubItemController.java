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

import com.example.clsoftlab.dto.pay.ClubItemDetailDto;
import com.example.clsoftlab.dto.pay.ClubItemRequestDto;
import com.example.clsoftlab.service.pay.ClubItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/club-item")
public class ClubItemController {

	private final ClubItemService clubItemService;
	
	public ClubItemController(ClubItemService clubItemService) {
		this.clubItemService = clubItemService;
	}
	
	// 검색어로 목록 조회
	@GetMapping("")
	public String getClubItemList (@RequestParam(required = false) List<String> clubCode, @RequestParam(required = false) String useYn, 
			@RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		int size = 1000;
		
		Page<ClubItemDetailDto> clubItemPage = clubItemService.searchClubItem(clubCode, useYn, page, size);
		
		model.addAttribute("clubItem", clubItemPage.getContent());
		model.addAttribute("clubCode", clubCode);
		model.addAttribute("useYn", useYn);
		model.addAttribute("clubItemPage", clubItemPage);
		model.addAttribute("searchClubItemList", clubItemService.getClubItemList());
		
		return "/pay/club-item/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewClubItem (@Valid @RequestBody ClubItemRequestDto dto) {
		clubItemService.addNewClubItem(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateClubItem (@Valid @RequestBody ClubItemRequestDto dto) {
		clubItemService.updateClubItem(dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 체크
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String clubCode) {
		boolean result = clubItemService.checkOverlap(clubCode);
		return ResponseEntity.ok(result);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<ClubItemDetailDto> findById (@PathVariable Long id) {
		return clubItemService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
