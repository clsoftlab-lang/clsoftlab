package com.example.clsoftlab.controller.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clsoftlab.dto.pay.PayCertificateDetailDto;
import com.example.clsoftlab.dto.pay.PayCertificateRequestDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.PayCertificateService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/pay/pay-certificate")
public class PayCertificateController {

	private final PayCertificateService payCertificateService;
	private final EmployeeMasterService employeeMasterService;
	
	public PayCertificateController(PayCertificateService payCertificateService, EmployeeMasterService employeeMasterService) {
		this.payCertificateService = payCertificateService;
		this.employeeMasterService = employeeMasterService;
	}
	
	// 검색어로 전체 목록 조회
	@GetMapping("")
	public String getPayCertificateList (@RequestParam(required = false) String year, @RequestParam(required = false) List<String> empNo,
			@RequestParam(required = false) List<String> periodType, @RequestParam(required = false, defaultValue = "0") Integer page, Model model) {
		
		int size = 1000;
		
		Page<PayCertificateDetailDto> payCertificatePage = payCertificateService.searchPayCertificate(empNo, year, periodType, page, size);
		
		model.addAttribute("payCertificate", payCertificatePage.getContent());
		model.addAttribute("year", year);
		model.addAttribute("empNo", empNo);
		model.addAttribute("periodType", periodType);
		model.addAttribute("payCertificatePage", payCertificatePage);
		model.addAttribute("employeeList", employeeMasterService.findAll());
		model.addAttribute("searchEmployeeList", payCertificateService.getEmployeeList());
		
		return "pay/pay-certificate/list";
	}
	
	// 새 명세표 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewPayCertificate (@Valid @RequestBody PayCertificateRequestDto dto) {
		payCertificateService.addNewPayCertificate(dto);
		
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String empNo, @RequestParam String year, @RequestParam String periodType,
			@RequestParam LocalDate periodFrom, @RequestParam LocalDate periodTo) {
		boolean result = payCertificateService.checkOverlap(empNo, year, periodType, periodFrom, periodTo);
		
		return ResponseEntity.ok(result);
	}
	
	// 세부 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<PayCertificateDetailDto> findById (@PathVariable long id) {
		return payCertificateService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
}
