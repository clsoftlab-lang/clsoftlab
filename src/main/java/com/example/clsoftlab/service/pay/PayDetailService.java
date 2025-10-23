package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.dto.pay.PayDetailDto;
import com.example.clsoftlab.dto.pay.PayDetailRequestDto;
import com.example.clsoftlab.dto.pay.PayDetailUpdateDto;
import com.example.clsoftlab.entity.PayDetail;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.PayDetailRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;
import com.example.clsoftlab.repository.pay.specification.PayDetailSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PayDetailService {

	private final PayDetailRepository payDetailRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final PayItemRepository payItemRepository;
	private final ModelMapper modelMapper;
	
	public PayDetailService(PayDetailRepository payDetailRepository, EmployeeMasterRepository employeeMasterRepository,
			PayItemRepository payItemRepository, ModelMapper modelMapper) {
		this.payDetailRepository = payDetailRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.payItemRepository = payItemRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 page 조회
	public Page<PayDetailDto> searchPayDetail (String payYmFrom, String payYmTo, Integer seq, List<String> empNo, List<String> itemCode,
			int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("payYm").descending().and(Sort.by("employeeMaster.pernr").descending()));
		
		Specification<PayDetail> spec = Specification.not(null);
		
		spec = spec.and(PayDetailSpecs.withEmpNos(empNo))
				.and(PayDetailSpecs.withPayYmRange(payYmFrom, payYmTo))
				.and(PayDetailSpecs.withSeqNo(seq))
				.and(PayDetailSpecs.withItemCodes(itemCode));
		
		return payDetailRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, PayDetailDto.class));
	}
	
	// 항목 추가
	@Transactional
	public void addNewDetail (PayDetailRequestDto dto) {
		PayDetail payDetail = modelMapper.map(dto, PayDetail.class);
		payDetail.setPayYm(dto.getPayYm().replace("-", ""));
		if (StringUtils.hasText(dto.getBackYm())) {
	        payDetail.setBackYm(dto.getBackYm().replace("-", ""));
	    }
		payDetail.setEmployeeMaster(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		payDetail.setPayItem(payItemRepository.getReferenceById(dto.getItemCode()));
		payDetailRepository.save(payDetail);
	}
	
	// 항목 수정
	@Transactional
	public void updateDetail (PayDetailUpdateDto dto) {
		if (dto.getId() == null) {
			throw new IllegalAccessError(".");
		}
		
		PayDetail payDetail = payDetailRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		
		payDetail.update(dto);
	}
	
	// 항목 삭제
	@Transactional
	public void deleteDetail (long id) {
		PayDetail payDetail = payDetailRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		payDetailRepository.delete(payDetail);
	}
	
	// 상세 정보 조회
	public Optional<PayDetailDto> findById (long id) {
		return payDetailRepository.findById(id)
				.map(i -> modelMapper.map(i, PayDetailDto.class));
	}
	
	// 중복 검사
	public boolean checkOverlap (String payYm, String empNo, String itemCode, Integer seqNo) {
		return payDetailRepository.existsByPayYmAndEmployeeMaster_PernrAndPayItem_ItemCodeAndSeqNo(payYm, empNo, itemCode, seqNo);
	}
}
