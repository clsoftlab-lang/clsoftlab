package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.TaxTableDetailDto;
import com.example.clsoftlab.dto.pay.TaxTableRequestDto;
import com.example.clsoftlab.dto.pay.TaxTableSearchRequestDto;
import com.example.clsoftlab.entity.TaxTable;
import com.example.clsoftlab.entity.id.TaxTableId;
import com.example.clsoftlab.repository.pay.TaxTableRepository;
import com.example.clsoftlab.repository.pay.specification.TaxTableSpecs;

import jakarta.transaction.Transactional;

@Service
public class TaxTableService {

	private final TaxTableRepository taxTableRepository;
	private final ModelMapper modelMapper;
	
	public TaxTableService(TaxTableRepository taxTableRepository, ModelMapper modelMapper) {
		this.taxTableRepository = taxTableRepository;
		this.modelMapper = modelMapper;
	}
	
	// 목록 조회
	public Page<TaxTableDetailDto> searchTaxTable (TaxTableSearchRequestDto search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id.year"));
		Specification<TaxTable> spec = Specification.not(null);
		spec = spec.and(TaxTableSpecs.withYear(search.getYear()));
		spec = spec.and(TaxTableSpecs.withFamilyCount(search.getFamilyCount()));
		spec = spec.and(TaxTableSpecs.greaterThanOrEqualToIncome(search.getIncomeFrom()));
		spec = spec.and(TaxTableSpecs.lessThanOrEqualToIncome(search.getIncomeTo()));
		
		return taxTableRepository.findAll(spec, pageable).map(i -> modelMapper.map(i, TaxTableDetailDto.class));		
	}
	
	// 중복 검사
	public long countOverlappingTaxTable (int year, long familyCount, long incomeAmount) {
		return taxTableRepository.countOverlappingTaxTable(year, familyCount, incomeAmount);
	}
	
	// 새 세율/구간 등록
	@Transactional
	public void addNewTaxTable (TaxTableRequestDto dto) {
		
		TaxTable taxTable = new TaxTable(dto);
		taxTableRepository.save(taxTable);
	}
	
	// 특정 id로 찾기
	public Optional<TaxTableDetailDto> findById (TaxTableId id) {
		return taxTableRepository.findById(id).map(i -> modelMapper.map(i, TaxTableDetailDto.class));
	}
	
	// 세율/구간 수정
	@Transactional
	public void updateTaxTable (TaxTableId id, TaxTableRequestDto dto) {
		
		TaxTable taxTable = taxTableRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 항목을 찾을 수 없습니다."));
		
		taxTable.update(dto);
	}
	
	// 세율/구간 삭제
	@Transactional
	public void deleteTaxTable (TaxTableId id) {
		
		TaxTable taxTable = taxTableRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 항목을 찾을 수 없습니다."));
		
		taxTableRepository.delete(taxTable);
	}
	
}
