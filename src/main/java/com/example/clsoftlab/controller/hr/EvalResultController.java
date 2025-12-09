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

import com.example.clsoftlab.dto.hr.EvalResultDetailDto;
import com.example.clsoftlab.dto.hr.EvaluationDetailDto;
import com.example.clsoftlab.dto.hr.EvaluationDetailRequestDto;
import com.example.clsoftlab.service.hr.EvalResultService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/hr/evaluation-result")
@RequiredArgsConstructor
public class EvalResultController {

	private final EvalResultService evaluationResultService;
	
	
	// 메인 페이지
	@GetMapping("")
	public String evaluationResult () {
		return "hr/evaluation-result/list";
	}
	
//	// 결과 목록 저장
//	@PutMapping("/{pernr}")
//	public ResponseEntity<?> saveResultList (@PathVariable String pernr, @Valid @RequestBody List<EvalResultRequestDto> dtoList) {
//		try {
//			evaluationResultService.saveResultList(pernr, dtoList);
//			return ResponseEntity.ok().build();
//		} catch (IllegalArgumentException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
	
	// 사번으로 result 조회
	@GetMapping("/{pernr}")
	public ResponseEntity<Page<EvalResultDetailDto>> findByPernr (@PathVariable String pernr, @RequestParam(required = false) Integer page) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<EvalResultDetailDto> resultPage = evaluationResultService.findByPernr(pernr, page, size);
		return ResponseEntity.ok(resultPage);
	}
	
	// resultId로 상세 점수 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<List<EvaluationDetailDto>> findById (@PathVariable Long id) {

		List<EvaluationDetailDto> detailList = evaluationResultService.findById(id);
		return ResponseEntity.ok(detailList);
	}
	
	// 상세 점수 목록 저장
	@PutMapping("/detail")
	public ResponseEntity<?> saveDetailList (@Valid @RequestBody List<EvaluationDetailRequestDto> dtoList) {
		evaluationResultService.saveDetailList(dtoList);
		return ResponseEntity.ok().build();
	}
}
