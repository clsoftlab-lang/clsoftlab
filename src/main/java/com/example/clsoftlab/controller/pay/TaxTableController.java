package com.example.clsoftlab.controller.pay;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.clsoftlab.dto.pay.TaxTableDetailDto;
import com.example.clsoftlab.dto.pay.TaxTableRequestDto;
import com.example.clsoftlab.dto.pay.TaxTableSearchRequestDto;
import com.example.clsoftlab.entity.id.TaxTableId;
import com.example.clsoftlab.service.pay.TaxTableService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pay/tax")
public class TaxTableController {
	
	private final TaxTableService taxTableService;
	
	public TaxTableController(TaxTableService taxTableService) {
		this.taxTableService = taxTableService;
	}
	

	@GetMapping("")
	public String getTaxTableList(@ModelAttribute TaxTableSearchRequestDto search, @RequestParam(required = false)Integer page,
			Model model) {
		
		if(page == null) {
			page = 0;
		}
		
		int size= 1000;
		
		Page<TaxTableDetailDto> taxPage = taxTableService.searchTaxTable(search, page, size);
		
		model.addAttribute("taxTable", taxPage.getContent());
		model.addAttribute("year", search.getYear());
		model.addAttribute("familyCount", search.getFamilyCount());
		model.addAttribute("incomeFrom", search.getIncomeFrom());
		model.addAttribute("incomeTo", search.getIncomeTo());
		
		return "pay/tax/list";
	}
	
	// 새 세율/기간 등록
	@PostMapping("")
	public ResponseEntity<Void> addNewTaxTable (@Valid @RequestBody TaxTableRequestDto dto) {
		
		taxTableService.addNewTaxTable(dto);
		return ResponseEntity.ok().build();
	}
	
	// 세율/기간 수정
	@PutMapping("")
	public ResponseEntity<Void> UpdateTaxTable (@ModelAttribute TaxTableId id, @RequestBody TaxTableRequestDto dto) {
		
		taxTableService.updateTaxTable(id, dto);
		return ResponseEntity.ok().build();
	}
	
	// 중복 검사용
	@ResponseBody
	@GetMapping("/checkOverlap")
	public long checkOverlappingTaxTable (@RequestParam int year, @RequestParam long familyCount, 
			@RequestParam long incomeAmount) {
		
		return taxTableService.countOverlappingTaxTable(year, familyCount, incomeAmount);
	}
	
	// 디테일 정보
	@GetMapping("/detail")
	public ResponseEntity<TaxTableDetailDto> findById (@ModelAttribute TaxTableId id) {
		
		return taxTableService.findById(id)
				.map(dto -> ResponseEntity.ok(dto))
				.orElse(ResponseEntity.notFound().build());
				
	}
	
	//삭제
	@DeleteMapping("")
	public ResponseEntity<Void> deleteById (@ModelAttribute TaxTableId id) {

		taxTableService.deleteTaxTable(id);
		
		return ResponseEntity.ok().build();
				
	}
	
}
