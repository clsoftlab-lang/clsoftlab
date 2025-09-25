package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.CalcOrderDetailDto;
import com.example.clsoftlab.dto.pay.CalcOrderRequestDto;
import com.example.clsoftlab.entity.CalcOrder;
import com.example.clsoftlab.entity.PayItem;
import com.example.clsoftlab.repository.pay.CalcOrderRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;

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
	public Page<CalcOrderDetailDto> searchCalcOrder (String itemCode, String groupCode, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("itemCode"));
		
		return calcOrderRepository.findByItemCodeContainingAndGroupCodeContainingAndUseYnContaining(itemCode, groupCode, useYn, pageable)
				.map(i -> modelMapper.map(i, CalcOrderDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewCalcOrder (CalcOrderRequestDto dto) {
		
		CalcOrder calcOrder = modelMapper.map(dto, CalcOrder.class);
		
		PayItem payItem = payItemRepository.findById(calcOrder.getItemCode())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. itemCode : " + calcOrder.getItemCode()));
		
		calcOrder.setPayItem(payItem);
		
		if (dto.getDependsOn() != null) {
			CalcOrder dependsOn = calcOrderRepository.findById(dto.getDependsOn())
					.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. dependsOn : " + dto.getDependsOn()));
			calcOrder.setDependsOn(dependsOn);
		}
		
		calcOrderRepository.save(calcOrder);
	}
	
	// 기존 항목 수정
	@Transactional
	public void upateNewCalcOrder (CalcOrderRequestDto dto) {
		CalcOrder calcOrder = calcOrderRepository.findById(dto.getItemCode())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. itemCode : " + dto.getItemCode()));
		
		calcOrder.update(dto);
		
		if (dto.getDependsOn() != null) {
			CalcOrder dependsOn = calcOrderRepository.findById(dto.getDependsOn())
					.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. depnedsOn : " + dto.getDependsOn()));
			
			calcOrder.setDependsOn(dependsOn);
		}
	}
	
	// 코드 중복 검사
	public boolean checkOverlap (String itemCode) {
		return calcOrderRepository.existsByItemCode(itemCode);
	}
	
	// 계산순서 중복 검사
	public boolean checkOverlap (Integer order, String groupCode) {
		return calcOrderRepository.existsByOrderAndGroupCode(order, groupCode);
	}
	
	// 상세 정보 조회
	public Optional<CalcOrderDetailDto> findById (String itemCode) {
		return calcOrderRepository.findById(itemCode)
				.map(i -> modelMapper.map(i, CalcOrderDetailDto.class));
	}
}
