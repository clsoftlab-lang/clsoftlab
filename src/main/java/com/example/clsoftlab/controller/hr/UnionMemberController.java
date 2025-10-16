package com.example.clsoftlab.controller.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clsoftlab.dto.hr.UnionMemberDetailDto;
import com.example.clsoftlab.dto.hr.UnionMemberRequestDto;
import com.example.clsoftlab.service.hr.UnionMemberService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/hr/union-member")
public class UnionMemberController {

	private final UnionMemberService unionMemberService;
	
	public UnionMemberController(UnionMemberService unionMemberService) {
		this.unionMemberService = unionMemberService;
	}
	
	// 메인 페이지
	@GetMapping("")
	public String unionMember () {
		return "hr/union-member/list";
	}
	
	// 노조 리스트 저장
	@PutMapping("/{pernr}")
	public ResponseEntity<?> saveMemberList (@PathVariable String pernr, @Valid @RequestBody List<UnionMemberRequestDto> dtoList) {
		try {
			unionMemberService.saveMemberList(pernr, dtoList);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 사번으로 page 조회
	@GetMapping("/detail/{pernr}")
	public ResponseEntity<Page<UnionMemberDetailDto>> findByPernr (@PathVariable String pernr, @RequestParam(required = false) Integer page) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<UnionMemberDetailDto> memberPage = unionMemberService.findByPernr(pernr, page, size);
		
		return ResponseEntity.ok(memberPage);
	}
}
