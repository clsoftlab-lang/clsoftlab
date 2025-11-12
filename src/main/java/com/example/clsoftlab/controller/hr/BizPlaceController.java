package com.example.clsoftlab.controller.hr;

import java.io.IOException;
import java.util.List;

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

import com.example.clsoftlab.dto.hr.BizPlaceDetailDto;
import com.example.clsoftlab.dto.hr.BizPlaceListDto;
import com.example.clsoftlab.dto.hr.BizPlaceRequestDto;
import com.example.clsoftlab.dto.hr.BizPlaceSearchDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.hr.BizPlaceExcelService;
import com.example.clsoftlab.service.hr.BizPlaceService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/hr/biz-place")
public class BizPlaceController {

	private final BizPlaceService bizPlaceService;
	private final BizPlaceExcelService bizPlaceExcelService;
	private final EmployeeMasterService employeeMasterService;
	
	public BizPlaceController(BizPlaceService bizPlaceService, BizPlaceExcelService bizPlaceExcelService, 
			EmployeeMasterService employeeMasterService) {
		this.bizPlaceService = bizPlaceService;
		this.bizPlaceExcelService = bizPlaceExcelService;
		this.employeeMasterService = employeeMasterService;
	}
	
	// 검색어로 전체 목록 조회
	@GetMapping("")
	public String getBizPlaceList (@ModelAttribute BizPlaceSearchDto search, @RequestParam(required = false, defaultValue = "0") Integer page,
			Model model) {
		int size = 1000;
		
		Page<BizPlaceListDto> bizPlacePage = bizPlaceService.searchBizPlace(search, page, size);
		
		model.addAttribute("bizPlace", bizPlacePage.getContent());
		model.addAttribute("bizCode", search.getBizCode());
		model.addAttribute("sido", search.getSido());
		model.addAttribute("sigungu", search.getSigungu());
		model.addAttribute("useYn", search.getUseYn());
		model.addAttribute("bizPlacePage", bizPlacePage);
		model.addAttribute("searchBizPlaceList", bizPlaceService.getBizPlaceListForSearch());
		model.addAttribute("employeeList", employeeMasterService.findAll());
		
		return "hr/biz-place/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewBizPlace (@Valid @RequestBody BizPlaceRequestDto dto) {
		bizPlaceService.addNewBizPlace(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateBizPlace (@Valid @RequestBody BizPlaceRequestDto dto) {
		bizPlaceService.updateBizPlace(dto);
		return ResponseEntity.ok().build();
	}
	
	// 코드 중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String bizCode) {
		boolean result = bizPlaceService.checkOverlap(bizCode);
		return ResponseEntity.ok(result);
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<BizPlaceDetailDto> findById (@PathVariable Long id) {
		return bizPlaceService.findByID(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// 엑셀 다운로드
	@GetMapping("/excel")
	public void downloadExcel(@ModelAttribute BizPlaceSearchDto search, HttpServletResponse response) throws IOException {
		
		
		// 1. 응답(Response) 헤더 설정
        // - 브라우저가 이 응답을 '엑셀 파일'로 인식하게 만듭니다.
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"biz-place.xlsx\"");

        // 2. 엑셀에 들어갈 데이터 조회
        // - 검색 조건에 맞는 전체 데이터를 (페이징 없이) 가져옵니다.
        List<BizPlaceListDto> list = bizPlaceService.findAllBizPlace(search);

        // 3. 엑셀 파일 생성 및 출력
        // - 조회된 데이터를 ExcelService로 넘겨 엑셀 파일을 생성하고,
        //   response의 OutputStream에 직접 파일을 씁니다.
        bizPlaceExcelService.generateBizPlaceExcel(list, response.getOutputStream());
	}
	
	// 검색어로 page 조회
		@GetMapping("/list")
		public ResponseEntity<Page<BizPlaceListDto>> getBizPlacePage (@ModelAttribute BizPlaceSearchDto search, @RequestParam(required = false, defaultValue = "0") Integer page,
				Model model) {
			int size = 1000;
			
			Page<BizPlaceListDto> bizPlacePage = bizPlaceService.searchBizPlace(search, page, size);
			
			return ResponseEntity.ok(bizPlacePage);
		}
}
