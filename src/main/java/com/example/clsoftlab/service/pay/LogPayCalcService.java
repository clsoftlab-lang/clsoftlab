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
import com.example.clsoftlab.dto.pay.LogPayCalcDetailDto;
import com.example.clsoftlab.dto.pay.LogPayCalcRequestDto;
import com.example.clsoftlab.dto.pay.PayItemSearchDto;
import com.example.clsoftlab.entity.LogPayCalc;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.LogPayCalcRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;
import com.example.clsoftlab.repository.pay.specification.LogPayCalcSpecs;

import jakarta.transaction.Transactional;

@Service
public class LogPayCalcService {

	private final LogPayCalcRepository logPayCalcRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final PayItemRepository payItemRepository;
	private final ModelMapper modelMapper;
	
	public LogPayCalcService(LogPayCalcRepository logPayCalcRepository, EmployeeMasterRepository employeeMasterRepository,
			PayItemRepository payItemRepository, ModelMapper modelMapper) {
		this.logPayCalcRepository = logPayCalcRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.payItemRepository = payItemRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 page 조회
	public Page<LogPayCalcDetailDto> searchLog (List<String> payYm, List<String> empNo, List<String> itemCode, List<Integer> seqNo,
			int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<LogPayCalc> spec = Specification.not(null);
		
		spec = spec.and(LogPayCalcSpecs.withPayYm(payYm))
				.and(LogPayCalcSpecs.withEmpNo(empNo))
				.and(LogPayCalcSpecs.withItemCode(itemCode))
				.and(LogPayCalcSpecs.withSeqNo(seqNo));
		
		return logPayCalcRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, LogPayCalcDetailDto.class));
	}
	
	// 새 log 등록 (or update)
	@Transactional
	public void addNewLog (LogPayCalcRequestDto dto) {
		
		Optional<LogPayCalc> logPayCalcOpt = logPayCalcRepository.findByPayYmAndSeqNoAndEmployee_PernrAndPayItem_ItemCodeAndStepNo(dto.getPayYm(), dto.getSeqNo(), dto.getEmpNo(), dto.getItemCode(), dto.getStepNo());
		logPayCalcOpt.ifPresent(logPayCalc -> logPayCalcRepository.delete(logPayCalc));

			
		LogPayCalc logPayCalc = modelMapper.map(dto, LogPayCalc.class);
		logPayCalc.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		logPayCalc.setPayItem(payItemRepository.getReferenceById(dto.getItemCode()));
		
		logPayCalcRepository.save(logPayCalc);
	}
	
	// 중복 검사
	public boolean checkOverlap (LogPayCalcRequestDto dto) {
		return logPayCalcRepository.existsByPayYmAndSeqNoAndEmployee_PernrAndPayItem_ItemCodeAndStepNo(dto.getPayYm(), dto.getSeqNo(), dto.getEmpNo(), dto.getItemCode(), dto.getStepNo());
	}
	
	// 검색용 payYm 리스트 조회
	public List<String> getPayYmList () {
		return logPayCalcRepository.getPayYmList();
	}
	
	// 검색용 사원 리스트 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return logPayCalcRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// 검색용 payItem 리스트 조회
	public List<PayItemSearchDto> getPayItemList () {
		return logPayCalcRepository.getPayItemList().stream()
				.map(i -> modelMapper.map(i, PayItemSearchDto.class))
				.toList();
	}
	
	// 검색용 seqNo 리스트 조회
	public List<Integer> getSeqNoList () {
		return logPayCalcRepository.getSeqNoList();
	}
}
