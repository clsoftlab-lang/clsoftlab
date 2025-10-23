package com.example.clsoftlab.controller.pay;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clsoftlab.dto.pay.PayDetailDto;
import com.example.clsoftlab.dto.pay.PayDetailRequestDto;
import com.example.clsoftlab.dto.pay.PayDetailUpdateDto;
import com.example.clsoftlab.service.common.EmployeeMasterService;
import com.example.clsoftlab.service.pay.PayDetailService;
import com.example.clsoftlab.service.pay.PayItemService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/pay/pay-detail")
public class PayDetailController {

	
	private final PayDetailService payDetailService;
	private final EmployeeMasterService employeeMasterService;
	private final PayItemService payItemService;
	
	public PayDetailController(PayDetailService payDetailService, EmployeeMasterService employeeMasterService,
			PayItemService payItemService) {
		this.payDetailService = payDetailService;
		this.employeeMasterService = employeeMasterService;
		this.payItemService = payItemService;
	}

	// 검색어로 detailPage 조회
	@GetMapping("")
	public String payDetailList(@RequestParam(required = false) String payYmFrom, @RequestParam(required = false) String payYmTo,
			@RequestParam(required = false) Integer seqNo, @RequestParam(required = false) List<String> empNo,
			@RequestParam(required = false) List<String> itemCode, @RequestParam(required = false) Integer page, 
			Model model) {
		if (page == null) {
			page = 0;
		}
		
		int size = 1000;
		
		Page<PayDetailDto> detailPage = payDetailService.searchPayDetail(payYmFrom, payYmTo, seqNo, empNo, itemCode, page, size);
		
		model.addAttribute("payDetail", detailPage.getContent());
		model.addAttribute("payYmFrom", payYmFrom);
		model.addAttribute("payYmTo", payYmTo);
		model.addAttribute("seqNo", seqNo);
		model.addAttribute("empNo", empNo);
		model.addAttribute("itemCode", itemCode);
		model.addAttribute("detailPage", detailPage);
		model.addAttribute("empList", employeeMasterService.findAll());
		model.addAttribute("payItemList", payItemService.findAll());
		
		return "pay/pay-detail/list";
	}
	
	//새 항목 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewDetail (@Valid @RequestBody PayDetailRequestDto dto) {
		payDetailService.addNewDetail(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 수정
	@PutMapping("")
	public ResponseEntity<Void> updateDetail (@Valid @RequestBody PayDetailUpdateDto dto) {
		payDetailService.updateDetail(dto);
		return ResponseEntity.ok().build();
	}
	
	// 기존 항목 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDetail (@PathVariable Long id) {
		payDetailService.deleteDetail(id);
		return ResponseEntity.ok().build();
	}
	
	// 상세 정보 조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<PayDetailDto> findById (@PathVariable Long id) {
		return payDetailService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//중복 검사
	@GetMapping("/checkOverlap")
	public ResponseEntity<Boolean> checkOverlap (@RequestParam String payYm, @RequestParam String empNo,
			@RequestParam String itemCode, @RequestParam Integer seqNo) {
		String formattedPayYm = payYm.replace("-", "");
		boolean result = payDetailService.checkOverlap(formattedPayYm, empNo, itemCode, seqNo);
		
		return ResponseEntity.ok(result);
	}
}
