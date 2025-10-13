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

import com.example.clsoftlab.dto.hr.EmployeeEduDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeEduRequestDto;
import com.example.clsoftlab.service.common.FileService;
import com.example.clsoftlab.service.hr.EmployeeEduService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/hr/employee-edu")
public class EmployeeEduController {

	private final EmployeeEduService employeeEduService;
	private final FileService fileService;
	
	public EmployeeEduController(EmployeeEduService employeeEduService, FileService fileService) {
		this.employeeEduService = employeeEduService;
		this.fileService = fileService;
	}
	
	// 메인 페이지
	@GetMapping("")
	public String employeeEdu () {
		return "hr/employee-edu/list";
	}
	
	// 교육 리스트 저장
	@PutMapping("/{pernr}")
	public ResponseEntity<?> saveEduList (@PathVariable String pernr, @Valid @RequestBody List<EmployeeEduRequestDto> dtoList) {
		try {
			employeeEduService.saveEduList(pernr, dtoList);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	// 사번으로 목록 조회
	@GetMapping("/detail/{pernr}")
	public ResponseEntity<Page<EmployeeEduDetailDto>> findByPernr (@PathVariable String pernr, @RequestParam(required =  false) Integer page) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<EmployeeEduDetailDto> eduPage = employeeEduService.findByPernr(pernr, page, size);
		
		return ResponseEntity.ok(eduPage); 
	}
	
	// 파일 업로드
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
