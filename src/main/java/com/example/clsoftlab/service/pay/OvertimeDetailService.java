package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.OvertimeDetailDto;
import com.example.clsoftlab.dto.pay.OvertimeDetailRequestDto;
import com.example.clsoftlab.dto.pay.OvertimeDetailSearchDto;
import com.example.clsoftlab.entity.OvertimeDetail;
import com.example.clsoftlab.repository.pay.OvertimeDetailRepository;
import com.example.clsoftlab.repository.pay.specification.OvertimeDetailSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OvertimeDetailService {

	private final OvertimeDetailRepository overtimeDetailRepository;
	private final ModelMapper modelMapper;
	
	public OvertimeDetailService(OvertimeDetailRepository overtimeDetailRepository, ModelMapper modelMapper) {
		this.overtimeDetailRepository = overtimeDetailRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<OvertimeDetailDto> searchOvertimeDetail (OvertimeDetailSearchDto search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("empNo"));
		
		Specification<OvertimeDetail> spec = Specification.not(null);
		spec = spec.and(OvertimeDetailSpecs.withEmpNo(search.getEmpNo()));
		spec = spec.and(OvertimeDetailSpecs.withDate(search.getDate()));
		spec = spec.and(OvertimeDetailSpecs.withType(search.getType()));
		
		return overtimeDetailRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, OvertimeDetailDto.class));
		
	}
	
	// 새 근로 실적 등록
	@Transactional
	public void addNewOvertimeDetail (OvertimeDetailRequestDto dto) {
		overtimeDetailRepository.save(modelMapper.map(dto, OvertimeDetail.class));
	}
	
	// 기존 근로 실적 수정
	@Transactional
	public void updateOvertimeDetail (long id, OvertimeDetailRequestDto dto) {
		OvertimeDetail overtimeDetail = overtimeDetailRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		overtimeDetail.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String empNo, LocalDate date, String type) {
		return overtimeDetailRepository.existsByEmpNoAndDateAndType(empNo, date, type);
	}
	
	// 중복 검사(수정용)
	public boolean checkOverlap (String empNo, LocalDate date, String type, long id) {
		return overtimeDetailRepository.existsByEmpNoAndDateAndTypeAndIdNot(empNo, date, type, id);
	}
	
	// 세부 정부 조회
	public Optional<OvertimeDetailDto> findById (long id) {
		return overtimeDetailRepository.findById(id)
				.map(i -> modelMapper.map(i, OvertimeDetailDto.class));
	}
}
