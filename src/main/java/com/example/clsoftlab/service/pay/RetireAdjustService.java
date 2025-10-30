package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.RetireAdjustDetailDto;
import com.example.clsoftlab.dto.pay.RetireAdjustRequestDto;
import com.example.clsoftlab.dto.pay.RetireSummaryDetailDto;
import com.example.clsoftlab.entity.RetireAdjust;
import com.example.clsoftlab.entity.RetireSummary;
import com.example.clsoftlab.repository.pay.RetireAdjustRepository;
import com.example.clsoftlab.repository.pay.RetireSummaryRepository;
import com.example.clsoftlab.repository.pay.specification.RetireAdjustSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RetireAdjustService {

	private final RetireAdjustRepository retireAdjustRepository;
	private final RetireSummaryRepository retireSummaryRepository;
	private final ModelMapper modelMapper;
	
	public RetireAdjustService(RetireAdjustRepository retireAdjustRepository, RetireSummaryRepository retireSummaryRepository, 
			ModelMapper modelMapper) {
		this.retireAdjustRepository = retireAdjustRepository;
		this.retireSummaryRepository = retireSummaryRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 page 조회
	public Page<RetireAdjustDetailDto> searchRetireAdjust (List<String> empNo, LocalDate retireDate, String adjType, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<RetireAdjust> spec = Specification.not(null);
		
		spec = spec.and(RetireAdjustSpecs.withEmpNo(empNo))
				.and(RetireAdjustSpecs.withRetireDate(retireDate))
				.and(RetireAdjustSpecs.withAdjType(adjType));
		
		return retireAdjustRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i , RetireAdjustDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewAdjust (RetireAdjustRequestDto dto) {
        RetireSummary retireSummary = retireSummaryRepository.findById(dto.getRetireSummaryId())
                .orElseThrow(() -> new EntityNotFoundException("해당 정산 항목을 찾을 수 없습니다. id : " + dto.getRetireSummaryId()));
        
        RetireAdjust newAdjust = modelMapper.map(dto, RetireAdjust.class);
        
        newAdjust.setRetireSummary(retireSummary); 
        retireSummary.getAdjustments().add(newAdjust);

        retireSummary.recalculatePayments();
        
        retireSummaryRepository.save(retireSummary);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateAdjust(RetireAdjustRequestDto dto) {
	    
	    RetireAdjust retireAdjust = retireAdjustRepository.findById(dto.getId())
	            .orElseThrow(() -> new EntityNotFoundException("해당 가감 항목을 찾을 수 없습니다. id: " + dto.getId()));

	    retireAdjust.update(dto);
	    
	    retireAdjust.getRetireSummary().recalculatePayments();
	}
	
	// 상세 정보 조회
	public Optional<RetireAdjustDetailDto> findById (Long id) {
		return retireAdjustRepository.findById(id)
				.map(i -> modelMapper.map(i, RetireAdjustDetailDto.class));
	}
	
	 // 검색용 리스트 조회
	public List<RetireSummaryDetailDto> getRetireSummaryList () {
		
		List<RetireSummary> list = retireAdjustRepository.getRetireSummaryList();
		list.sort(Comparator.comparing(i -> i.getEmployee().getName()));
		
		return list.stream()
	            .map(i -> modelMapper.map(i, RetireSummaryDetailDto.class))
	            .toList();
	}
}
