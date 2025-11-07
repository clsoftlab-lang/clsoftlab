package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.CalcOrderDetailDto;
import com.example.clsoftlab.dto.pay.CalcOrderRequestDto;
import com.example.clsoftlab.dto.pay.CalcOrderSimpleDetailDto;
import com.example.clsoftlab.entity.CalcOrder;
import com.example.clsoftlab.repository.pay.CalcOrderRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;
import com.example.clsoftlab.repository.pay.specification.CalcOrderSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CalcOrderService {


	private final CalcOrderRepository calcOrderRepository;
	private final PayItemRepository payItemRepository;
	private final ModelMapper modelMapper;
	
	public CalcOrderService(CalcOrderRepository calcOrderRepository, PayItemRepository payItemRepository, 
			ModelMapper modelMapper) {
		this.calcOrderRepository = calcOrderRepository;
		this.payItemRepository = payItemRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<CalcOrderDetailDto> searchCalcOrder (List<String> itemCode, List<String> groupCode, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<CalcOrder> spec = Specification.not(null);
		
		spec = spec.and(CalcOrderSpecs.withItemCode(itemCode))
				.and(CalcOrderSpecs.withGroupCode(groupCode))
				.and(CalcOrderSpecs.withUseYn(useYn));
		
		return calcOrderRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, CalcOrderDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewCalcOrder (CalcOrderRequestDto dto) {
		
		CalcOrder calcOrder = modelMapper.map(dto, CalcOrder.class);
		
		calcOrder.setPayItem(payItemRepository.getReferenceById(dto.getItemCode()));
		
		if (dto.getDependsOnId() != null) {
			calcOrder.setDependsOn(calcOrderRepository.getReferenceById(dto.getDependsOnId()));
		}
		
		calcOrderRepository.save(calcOrder);
	}
	
	// 기존 항목 수정
	@Transactional
	public void upateNewCalcOrder (CalcOrderRequestDto dto) {
		CalcOrder calcOrder = calcOrderRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. itemCode : " + dto.getId()));
		
		calcOrder.update(dto);
		
		if (dto.getDependsOnId() != null) {
			
			calcOrder.setDependsOn(calcOrderRepository.getReferenceById(dto.getDependsOnId()));
		}
	}
	
	// 코드 중복 검사
	public boolean checkOverlap (String itemCode) {
		return calcOrderRepository.existsByPayItem_ItemCode(itemCode);
	}
	
	// 계산순서 중복 검사
	public boolean checkOverlap (Integer order, String groupCode, String itemCode) {
		return calcOrderRepository.existsByOrderAndGroupCodeAndPayItem_ItemCodeNot(order, groupCode, itemCode);
	}
	
	// 상세 정보 조회
	public Optional<CalcOrderDetailDto> findById (Long id) {
		return calcOrderRepository.findById(id)
				.map(i -> modelMapper.map(i, CalcOrderDetailDto.class));
	}
	
	// 검색용 CalcOrder 리스트 조회
	public List<CalcOrderSimpleDetailDto> findAll () {
		return calcOrderRepository.findAllByOrderByPayItem_ItemName().stream()
				.map(i -> modelMapper.map(i, CalcOrderSimpleDetailDto.class))
				.toList();
	}
}
