package com.example.clsoftlab.controller.hr;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.clsoftlab.dto.hr.OrgUnitDetailDto;
import com.example.clsoftlab.dto.hr.OrgUnitExcelDto;
import com.example.clsoftlab.dto.hr.OrgUnitFlatDto;
import com.example.clsoftlab.dto.hr.OrgUnitRequestDto;
import com.example.clsoftlab.dto.hr.OrgUnitTreeDto;
import com.example.clsoftlab.service.hr.BizPlaceService;
import com.example.clsoftlab.service.hr.OrgUnitExcelService;
import com.example.clsoftlab.service.hr.OrgUnitService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/hr/org-unit")
public class OrgUnitController {

	private final OrgUnitService orgUnitService;
	private final OrgUnitExcelService orgUnitExcelService;
	private final BizPlaceService bizPlaceServcie;
	
	public OrgUnitController(OrgUnitService orgUnitService, OrgUnitExcelService orgUnitExcelService, 
			BizPlaceService bizPlaceServcie) {
		this.orgUnitService = orgUnitService;
		this.orgUnitExcelService = orgUnitExcelService;
		this.bizPlaceServcie = bizPlaceServcie;
	}
	
	// 메인화면 : bizPlace 리스트만 전달
	@GetMapping("")
	public String orgUnit (Model model) {
		model.addAttribute("bizPlace", bizPlaceServcie.getBizPlaceList());
		
		return "hr/org-unit/list";
	}
	
	// 새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewOrgUnit (@Valid @RequestBody OrgUnitRequestDto dto) {
		orgUnitService.addNewOrgUnit(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateOrgUnit (@Valid @RequestBody OrgUnitRequestDto dto) {
		orgUnitService.updateOrgUnit(dto);
		return ResponseEntity.ok().build();
	}
	
	// 검색어로 조회후, tree 반환
	@ResponseBody
	@GetMapping("/tree")
	public List<OrgUnitTreeDto> getOrgUnitTree (@RequestParam(required = false) List<String> bizCode, @RequestParam(required = false) List<String> orgName,
			@RequestParam(required = false) String useYn) {
		
		return orgUnitService.getOrgUnitTree(bizCode, orgName, useYn);
	}
	
	// 중복 검사
	@ResponseBody
	@GetMapping("/checkOverlap")
	public boolean checkOverlap (@RequestParam String orgCode) {
		return orgUnitService.checkOverlap(orgCode);
	}
	
	// 상세 정보 조회
	@ResponseBody
	@GetMapping("/detail/{id}")
	public ResponseEntity<OrgUnitDetailDto> findById (@PathVariable Long id) {
		return orgUnitService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// 검색어로 목록 조회
	@ResponseBody
	@GetMapping("/list")
	public Page<OrgUnitFlatDto> getOrgUnitList (@RequestParam(required = false) List<String> bizCode, @RequestParam(required = false) List<String> orgName,
			@RequestParam(required = false) String useYn, @RequestParam(required = false, defaultValue = "0") Integer page) {
		
		int size= 1000;
		
		return orgUnitService.getOrgUnitList(bizCode, orgName, useYn, page, size);
	}
	
	// 엑셀 다운로드
	@GetMapping("/excel")
	public void downloadExcel (@RequestParam(required = false) List<String> bizCode, @RequestParam(required = false) List<String> orgName, 
			@RequestParam(required = false) String useYn,HttpServletResponse response) throws IOException {
		// 1. 응답(Response) 헤더 설정
        // - 브라우저가 이 응답을 '엑셀 파일'로 인식하게 만듭니다.
		
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"org-Unit.xlsx\"");

        // 2. 엑셀에 들어갈 데이터 조회
        // - 검색 조건에 맞는 전체 데이터를 (페이징 없이) 가져옵니다.
        List<OrgUnitExcelDto> list = orgUnitService.getExcelList(bizCode, orgName, useYn);

        // 3. 엑셀 파일 생성 및 출력
        // - 조회된 데이터를 ExcelService로 넘겨 엑셀 파일을 생성하고,
        //   response의 OutputStream에 직접 파일을 씁니다.
        orgUnitExcelService.generateOrgUnitExcel(list, response.getOutputStream());
	}
}
