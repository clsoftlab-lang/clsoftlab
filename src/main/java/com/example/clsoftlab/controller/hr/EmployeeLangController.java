package com.example.clsoftlab.controller.hr;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.clsoftlab.dto.hr.EmployeeLangDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeLangRequestDto;
import com.example.clsoftlab.service.common.FileService;
import com.example.clsoftlab.service.hr.EmployeeLangService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/hr/employee-lang")
public class EmployeeLangController {

	private final EmployeeLangService employeeLangService;
	private final FileService fileService;
	
	public EmployeeLangController(EmployeeLangService employeeLangService, FileService fileService) {
		this.employeeLangService = employeeLangService;
		this.fileService = fileService;
	}
	
	// 메인 페이지
	@GetMapping("")
	public String employeeLang ( ) {
		return "hr/employee-lang/list";
	}
	
	// 어학 리스트 저장
	@PutMapping("/{pernr}")
	public ResponseEntity<?> saveEmployeeLang (@PathVariable String pernr, @Valid @RequestBody List<EmployeeLangRequestDto> dtoList) {
		try {
			employeeLangService.saveEmployeeLangList(pernr, dtoList);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 사번으로 조회
	@GetMapping("/detail/{pernr}")
	public ResponseEntity<Page<EmployeeLangDetailDto>> findByPernr (@PathVariable String pernr, @RequestParam(required = false) Integer page) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<EmployeeLangDetailDto> employeeLangPage = employeeLangService.searchEmployeeLang(pernr, page, size);
		return ResponseEntity.ok(employeeLangPage);
	}
	
	// 자격증 파일 업로드
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {
        try {
            String attachId = fileService.saveFile(file);
            // 성공 시, attachId를 JSON 형태로 반환
            return ResponseEntity.ok().body(Map.of("attachId", attachId));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("파일 업로드에 실패했습니다.");
        }
    }
}
