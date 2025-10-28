package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.PaySummaryMainDetailDto;
import com.example.clsoftlab.dto.pay.PaySummaryMainRequestDto;
import com.example.clsoftlab.entity.PaySummaryMain;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.PaySummaryMainRepository;
import com.example.clsoftlab.repository.pay.specification.PaySummaryMainSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public class PaySummaryMainService {

	private final PaySummaryMainRepository paySummaryMainRepository;
	private final ModelMapper modelMapper;
	
	public PaySummaryMainService(PaySummaryMainRepository paySummaryMainRepository, EmployeeMasterRepository employeeMasterRepository,
			ModelMapper modelMapper) {
		this.paySummaryMainRepository = paySummaryMainRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 page 조회
	public Page<PaySummaryMainDetailDto> searchPaySummary (String payYm, List<String> empNo, Integer seqNo, String isFinal, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<PaySummaryMain> spec = Specification.not(null);
		
		spec = spec.and(PaySummaryMainSpecs.withPayYm(payYm))
				.and(PaySummaryMainSpecs.withEmpNo(empNo))
				.and(PaySummaryMainSpecs.withIsFinal(isFinal));
		
		return paySummaryMainRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, PaySummaryMainDetailDto.class));
	}
	
	// 기존 항목 수정
	@Transactional
	public void updatePaySummary (PaySummaryMainRequestDto dto) {
		PaySummaryMain paySummaryMain = paySummaryMainRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		
		paySummaryMain.update(dto);
	}
	
	// 상세 정보 조회
	public Optional<PaySummaryMainDetailDto> findById (long id) {
		return paySummaryMainRepository.findById(id)
				.map(i -> modelMapper.map(i, PaySummaryMainDetailDto.class));
	}
	
	// 사번 list 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return paySummaryMainRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
}
