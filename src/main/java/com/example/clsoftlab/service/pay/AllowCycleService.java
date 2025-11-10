package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.AllowCycleDetailDto;
import com.example.clsoftlab.dto.pay.AllowCycleRequestDto;
import com.example.clsoftlab.dto.pay.PayItemSearchDto;
import com.example.clsoftlab.entity.AllowCycle;
import com.example.clsoftlab.repository.pay.AllowCycleRepository;
import com.example.clsoftlab.repository.pay.PayItemRepository;
import com.example.clsoftlab.repository.pay.specification.AllowCycleSpecs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AllowCycleService {

	private final AllowCycleRepository allowCycleRepository;
	private final PayItemRepository payItemRepository;
	private final ModelMapper modelMapper;
	
	public AllowCycleService(AllowCycleRepository allowCycleRepository, PayItemRepository payItemRepository, 
			ModelMapper modelMapper, EntityManager entityManager) {
		this.allowCycleRepository = allowCycleRepository;
		this.payItemRepository = payItemRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 목록 조회
	public Page<AllowCycleDetailDto> searchAllowCycle (List<String> itemCode, List<String> cycle, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<AllowCycle> spec = Specification.not(null);
		
		spec = spec.and(AllowCycleSpecs.withItemCode(itemCode))
				.and(AllowCycleSpecs.withCycle(cycle))
				.and(AllowCycleSpecs.withUseYn(useYn));
		
		return allowCycleRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, AllowCycleDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewAllowCycle (AllowCycleRequestDto dto) {
		AllowCycle allowCycle = modelMapper.map(dto, AllowCycle.class);
		allowCycle.setPayItem(payItemRepository.getReferenceById(dto.getItemCode()));

		allowCycleRepository.save(allowCycle);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateAllowCycle (AllowCycleRequestDto dto) {
		AllowCycle allowCycle = allowCycleRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 항목 입니다. id : " + dto.getId()));
		
		allowCycle.update(dto);
	}
	
	// 코드 중복 검사
	public boolean checkOverlap (String itemCode) {
		return allowCycleRepository.existsByPayItem_ItemCode(itemCode);
	}
	
	// 상세 정보 조회
	public Optional<AllowCycleDetailDto> findById (Long id) {
		return allowCycleRepository.findById(id)
				.map(i -> modelMapper.map(i, AllowCycleDetailDto.class));
	}
	
	// 검색용 payItem 리스트 조회
	public List<PayItemSearchDto> getPayItemList () {
		return allowCycleRepository.getPayItemList().stream()
				.map(i -> modelMapper.map(i, PayItemSearchDto.class))
				.toList();
	}
}
