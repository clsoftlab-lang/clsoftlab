package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.PaySummaryMainDetailDto;
import com.example.clsoftlab.dto.pay.PaySummaryMainRequestDto;
import com.example.clsoftlab.entity.PaySummaryMain;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.PaySummaryMainRepository;
import com.example.clsoftlab.repository.pay.specification.PaySummaryMainSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class PaySummaryMainService {

	private final PaySummaryMainRepository paySummaryMainRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public PaySummaryMainService(PaySummaryMainRepository paySummaryMainRepository, EmployeeMasterRepository employeeMasterRepository,
			ModelMapper modelMapper) {
		this.paySummaryMainRepository = paySummaryMainRepository;
		this.employeeMasterRepository = employeeMasterRepository;
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
	
	// 새 항목 등록
	@Transactional
	public void addNewPaySummary (@Valid @RequestBody PaySummaryMainRequestDto dto) {
		PaySummaryMain paySummaryMain = modelMapper.map(dto, PaySummaryMain.class);
		paySummaryMain.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		
		paySummaryMainRepository.save(paySummaryMain);
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
	
	// 중복 검사
	public boolean checkOverlap (String payYm, String empNo, Integer seqNo, Long id) {
		
		boolean result;
		if (id == null) {
			result = paySummaryMainRepository.existsByEmployee_PernrAndPayYmAndSeqNo(empNo, payYm, seqNo);
		} else {
			result = paySummaryMainRepository.existsByEmployee_PernrAndPayYmAndSeqNoAndIdNot(empNo, payYm, seqNo, id);
		}
		
		return result;
	}
	
}
