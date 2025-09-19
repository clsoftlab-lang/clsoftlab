package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.PayItemDetailDto;
import com.example.clsoftlab.dto.pay.PayItemListDto;
import com.example.clsoftlab.dto.pay.PayItemRequestDto;
import com.example.clsoftlab.entity.PayItem;
import com.example.clsoftlab.repository.pay.PayItemRepository;

import jakarta.transaction.Transactional;

@Service
public class PayItemService {

	private final PayItemRepository payItemRepository;
	private final ModelMapper modelMapper;
	
	public PayItemService(PayItemRepository payItemRepository, ModelMapper modelMapper) {
		this.payItemRepository = payItemRepository;
		this.modelMapper = modelMapper;
	}
	
	
	// 급여 항목 목록 검색
	public Page<PayItemListDto> searchPayItem(String itemName, String itemType, String useYn, int page, int size) {
	
		Pageable pageable = PageRequest.of(page, size, Sort.by("itemName"));
		return payItemRepository.search(itemName, itemType, useYn, pageable).map(i -> modelMapper.map(i, PayItemListDto.class));
	}
	
	// 항목코드로 검색
	public Optional<PayItemDetailDto> findByCode (String itemCode) {
		return payItemRepository.findById(itemCode).map(i -> modelMapper.map(i, PayItemDetailDto.class));
	}
	
	// 새로운 급여 항목 추가
	@Transactional
	public void addNewPayItem(PayItemRequestDto payItem) {
		
		payItemRepository.save(modelMapper.map(payItem, PayItem.class));
		return;
	}
	
	// 급여 항목 수정
	@Transactional
	public void updatePayItem(String itemCode, PayItemRequestDto payItemDto) { 
	
		PayItem payItem = payItemRepository.findById(itemCode)
				.orElseThrow(() -> new IllegalArgumentException("해당 항목을 찾을 수 없습니다. code=" + itemCode));

		payItem.update(payItemDto);
	}
	
	//DEDUCT만 검색
	public Page<PayItemListDto> searchDeduct (String itemName, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("itemCode"));
		
		return payItemRepository.findByItemNameContainingAndUseYnContainingAndItemTypeContaining(itemName, useYn, "DEDUCT", pageable)
				.map(i -> modelMapper.map(i, PayItemListDto.class));
		
	}
}
