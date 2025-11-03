package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.PayDeductReasonDetailDto;
import com.example.clsoftlab.dto.pay.PayDeductReasonRequestDto;
import com.example.clsoftlab.dto.pay.PayItemSearchDto;
import com.example.clsoftlab.entity.PayDeductReason;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.PayDeductReasonRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;
import com.example.clsoftlab.repository.pay.specification.PayDeductReasonSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service

public class PayDeductReasonService {

	private final PayDeductReasonRepository payDeductReasonRepository;
	private final PayItemRepository payItemRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public PayDeductReasonService(PayDeductReasonRepository payDeductReasonRepository, PayItemRepository payItemRepository,
			EmployeeMasterRepository employeeMasterRepository, ModelMapper modelMapper) {
		this.payDeductReasonRepository = payDeductReasonRepository;
		this.payItemRepository = payItemRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<PayDeductReasonDetailDto> searchPayDeductReason (List<String> empNo, String payYm, 
			List<String> itemCode, int size, int page) {
		
		Pageable pageable = PageRequest.of(page, size);
		Specification<PayDeductReason> spec = Specification.not(null);
		
		spec = spec.and(PayDeductReasonSpecs.withEmpNo(empNo))
				.and(PayDeductReasonSpecs.withPayYm(payYm))
				.and(PayDeductReasonSpecs.withItemCode(itemCode));

		return payDeductReasonRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, PayDeductReasonDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewPayDeductReason (PayDeductReasonRequestDto dto) {
		PayDeductReason payDeductReason = modelMapper.map(dto, PayDeductReason.class);
		payDeductReason.setPayItem(payItemRepository.getReferenceById(dto.getItemCode()));
		payDeductReason.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		payDeductReasonRepository.save(payDeductReason);
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
		return payDeductReasonRepository.existsByEmployee_PernrAndPayYmAndSeqNoAndPayItem_ItemCode(empNo, payYm, seqNo, itemCode);
	}
	
	// 디테일 정보 조회
	public Optional<PayDeductReasonDetailDto> findById (long id) {
		return payDeductReasonRepository.findById(id)
				.map(i -> modelMapper.map(i, PayDeductReasonDetailDto.class));
	}
	
	// 검색용 사번 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return payDeductReasonRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// 검색용 payItem 조회
	public List<PayItemSearchDto> getPayItemList () {
		return payDeductReasonRepository.getPayItemList().stream()
				.map(i -> modelMapper.map(i, PayItemSearchDto.class))
				.toList();
	}
}
