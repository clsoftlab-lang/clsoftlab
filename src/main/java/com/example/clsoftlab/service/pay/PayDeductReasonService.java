package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.PayDeductReasonDetailDto;
import com.example.clsoftlab.dto.pay.PayDeductReasonRequestDto;
import com.example.clsoftlab.entity.PayDeductReason;
import com.example.clsoftlab.repository.PayDeductReasonRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service

public class PayDeductReasonService {

	private final PayDeductReasonRepository payDeductReasonRepository;
	private final ModelMapper modelMapper;
	
	public PayDeductReasonService(PayDeductReasonRepository payDeductReasonRepository,
			ModelMapper modelMapper) {
		this.payDeductReasonRepository = payDeductReasonRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<PayDeductReasonDetailDto> searchPayDeductReason (String empNo, String payYm, 
			String itemCode, int size, int page) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("payYm").descending());
		
		return payDeductReasonRepository.findByEmpNoContainingAndPayYmContainingAndItemCodeContaining(empNo, payYm, itemCode, pageable)
				.map(i -> modelMapper.map(i, PayDeductReasonDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewPayDeductReason (PayDeductReasonRequestDto dto) {
		payDeductReasonRepository.save(modelMapper.map(dto, PayDeductReason.class));
	}
	
	// 항목 수정
	@Transactional
	public void updatePayDeductReason (long id, PayDeductReasonRequestDto dto) {
		PayDeductReason payDeductReason = payDeductReasonRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		payDeductReason.update(dto);
	}
	
	// 항목 삭제
	@Transactional
	public void deletePayDeductReason (long id) {
		PayDeductReason payDeductReason = payDeductReasonRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		payDeductReasonRepository.delete(payDeductReason);
	}
	
	// 중복 검사
	public boolean checkOverlappingPayDeductReason (String empNo, String payYm, int seqNo, String itemCode) {
		return payDeductReasonRepository.existsByEmpNoAndPayYmAndSeqNoAndItemCode(empNo, payYm, seqNo, itemCode);
	}
	
	// 중복 검사(수정용)
	public boolean checkOverlappingPayDeductReason (String empNo, String payYm, int seqNo, String itemCode, long id) {
		return payDeductReasonRepository.existsByEmpNoAndPayYmAndSeqNoAndItemCodeAndIdNot(empNo, payYm, seqNo, itemCode, id);
	}
	
	// 디테일 정보
	public Optional<PayDeductReasonDetailDto> findById (long id) {
		return payDeductReasonRepository.findById(id)
				.map(i -> modelMapper.map(i, PayDeductReasonDetailDto.class));
	}
	
}
