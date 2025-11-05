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
import com.example.clsoftlab.dto.pay.PayItemSearchDto;
import com.example.clsoftlab.dto.pay.PayRoundHistoryDetailDto;
import com.example.clsoftlab.entity.PayRoundHistory;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;
import com.example.clsoftlab.repository.pay.PayRoundHistoryRepository;
import com.example.clsoftlab.repository.pay.specification.PayRoundHistorySpecs;

@Service
public class PayRoundHistoryService {

	private final PayRoundHistoryRepository payRoundHistoryRepository;
	private final ModelMapper modelMapper;
	
	public PayRoundHistoryService(PayRoundHistoryRepository payRoundHistoryRepository, EmployeeMasterRepository employeeMasterRepository,
			PayItemRepository payItemRepository, ModelMapper modelMapper) {
		this.payRoundHistoryRepository = payRoundHistoryRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 목록 조회
	public Page<PayRoundHistoryDetailDto> searchPayRoundHistory (List<String> empNo, String payYm, List<String> itemCode, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<PayRoundHistory> spec = Specification.not(null);
		
		spec = spec.and(PayRoundHistorySpecs.withEmpNo(empNo))
				.and(PayRoundHistorySpecs.withPayYm(payYm))
				.and(PayRoundHistorySpecs.withItemCode(itemCode));
		
		return payRoundHistoryRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, PayRoundHistoryDetailDto.class));
	}
	
	// 상세 정보 조회
	public Optional<PayRoundHistoryDetailDto> findById (long id) {
		return payRoundHistoryRepository.findById(id)
				.map(i -> modelMapper.map(i, PayRoundHistoryDetailDto.class));
	}
	
	// 검색용 사원 list 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return payRoundHistoryRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// 검색용 payItem list 조회
	public List<PayItemSearchDto> getPayItemList () {
		return payRoundHistoryRepository.getPayItemList().stream()
				.map(i -> modelMapper.map(i, PayItemSearchDto.class))
				.toList();
	}
}
