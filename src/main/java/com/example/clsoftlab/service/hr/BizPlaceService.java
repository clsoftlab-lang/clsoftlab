package com.example.clsoftlab.service.hr;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.BizPlaceDetailDto;
import com.example.clsoftlab.dto.hr.BizPlaceListDto;
import com.example.clsoftlab.dto.hr.BizPlaceRequestDto;
import com.example.clsoftlab.dto.hr.BizPlaceSearchDto;
import com.example.clsoftlab.dto.hr.BizPlaceSimpleDto;
import com.example.clsoftlab.entity.BizPlace;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.hr.BizPlaceRepository;
import com.example.clsoftlab.repository.hr.specification.BizPlaceSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BizPlaceService {

	private final BizPlaceRepository bizPlaceRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public BizPlaceService(BizPlaceRepository bizPlaceRepository, EmployeeMasterRepository employeeMasterRepository,
			ModelMapper modelMapper) {
		this.bizPlaceRepository = bizPlaceRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 목록 조회
	public Page<BizPlaceListDto> searchBizPlace (BizPlaceSearchDto search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<BizPlace> spec = Specification.not(null);
		
		spec = spec.and(BizPlaceSpecs.withBizCode(search.getBizCode()))
				.and(BizPlaceSpecs.withAddress(search.getSido(),search.getSigungu()))
				.and(BizPlaceSpecs.withUseYn(search.getUseYn()));
		
		return bizPlaceRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, BizPlaceListDto.class));
	}
	
	// 검색어로 목록 조회 (page 제한 없음)
	public List<BizPlaceListDto> findAllBizPlace (BizPlaceSearchDto search) {
		Specification<BizPlace> spec = Specification.not(null);
		
		spec = spec.and(BizPlaceSpecs.withBizCode(search.getBizCode()))
			.and(BizPlaceSpecs.withAddress(search.getSido(),search.getSigungu()))
			.and(BizPlaceSpecs.withUseYn(search.getUseYn()));
		
		return bizPlaceRepository.findAll(spec).stream()
				.map(i -> modelMapper.map(i, BizPlaceListDto.class))
				.toList();
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewBizPlace (BizPlaceRequestDto dto) {
		BizPlace bizPlace = modelMapper.map(dto, BizPlace.class);
		bizPlace.setManager(employeeMasterRepository.getReferenceById(dto.getManagerId()));
		bizPlaceRepository.save(bizPlace);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateBizPlace (BizPlaceRequestDto dto) {
		BizPlace bizPlace = bizPlaceRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. bizCode : " + dto.getId()));
		
		bizPlace.update(dto);
		if (dto.getManagerId() != null) {
			bizPlace.setManager(employeeMasterRepository.getReferenceById(dto.getManagerId()));
		}
	}
	
	// 중복 검사
	public boolean checkOverlap (String bizCode) {
		return bizPlaceRepository.existsByBizCode(bizCode);
	}
	
	// 상세 정보 조회
	public Optional<BizPlaceDetailDto> findByID(Long id) {
		return bizPlaceRepository.findById(id)
				.map(i -> modelMapper.map(i, BizPlaceDetailDto.class));
	}
	
	// bizPlace list 반환 (useYn)
	public List<BizPlaceListDto> getBizPlaceList () {
		return bizPlaceRepository.findAllByUseYnOrderByBizName("Y")
				.stream()
				.map(i -> modelMapper.map(i, BizPlaceListDto.class))
				.toList();
	}
	
	// 검색용 bizPlace 리스트 조회
	public List<BizPlaceSimpleDto> getBizPlaceListForSearch () {
		return bizPlaceRepository.findAllByOrderByBizName().stream()
				.map(i -> modelMapper.map(i, BizPlaceSimpleDto.class))
				.toList();
	}
}
