package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.SimplifiedTaxTableDetailDto;
import com.example.clsoftlab.dto.pay.SimplifiedTaxTableRequestDto;
import com.example.clsoftlab.entity.SimplifiedTaxTable;
import com.example.clsoftlab.repository.pay.SimplifiedTaxTableRepository;
import com.example.clsoftlab.repository.pay.specification.SimplifiedTaxTableSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SimplifiedTaxTableService {

	private final SimplifiedTaxTableRepository simplifiedTaxTableRepository;
	private final ModelMapper modelMapper;
	
	public SimplifiedTaxTableService(SimplifiedTaxTableRepository simplifiedTaxTableRepository, ModelMapper modelMapper) {
		this.simplifiedTaxTableRepository = simplifiedTaxTableRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 페이지 조회
	public Page<SimplifiedTaxTableDetailDto> searchTaxTable (String year, Integer familyCount, Long incomeAmtFrom,
			Long incomeAmtTo, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<SimplifiedTaxTable> spec = Specification.not(null);
		
		spec = spec.and(SimplifiedTaxTableSpecs.wihtYear(year))
				.and(SimplifiedTaxTableSpecs.withFamilyCount(familyCount))
				.and(SimplifiedTaxTableSpecs.withIncomeAmtRange(incomeAmtFrom, incomeAmtTo));
		
		return simplifiedTaxTableRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, SimplifiedTaxTableDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewTaxTable (SimplifiedTaxTableRequestDto dto) {
		simplifiedTaxTableRepository.save(modelMapper.map(dto, SimplifiedTaxTable.class));
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateTaxTable (SimplifiedTaxTableRequestDto dto) {
		SimplifiedTaxTable taxTable = simplifiedTaxTableRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		
		taxTable.update(dto);
	}
	
	// 상세 정보 조회
	public Optional<SimplifiedTaxTableDetailDto> findById (long id) {
		return simplifiedTaxTableRepository.findById(id)
				.map(i -> modelMapper.map(i, SimplifiedTaxTableDetailDto.class));
	}
	
	// 중복 검사
	public boolean checkOverlap (String year, Integer familyCount, Long incomeAmt) {
		return simplifiedTaxTableRepository.existsByYearAndFamilyCountAndIncomeAmt(year, familyCount, incomeAmt);
	}
}
