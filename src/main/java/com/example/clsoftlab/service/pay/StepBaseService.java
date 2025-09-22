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

import com.example.clsoftlab.dto.pay.StepBaseDetailDto;
import com.example.clsoftlab.dto.pay.StepBaseRequestDto;
import com.example.clsoftlab.entity.StepBase;
import com.example.clsoftlab.repository.pay.StepBaseRepository;
import com.example.clsoftlab.repository.pay.specification.StepBaseSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class StepBaseService {
	
	private final StepBaseRepository stepBaseRepository;
	private final ModelMapper modelMapper;
	
	public StepBaseService(StepBaseRepository stepBaseRepository, ModelMapper modelMapper) {
		this.stepBaseRepository = stepBaseRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 목록 조회
	public Page<StepBaseDetailDto> searchStepBase (String gradeCode, Integer stepNo, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("fromDate").descending());
		Specification<StepBase> spec = Specification.not(null);
		spec = spec.and(StepBaseSpecs.withGradeCode(gradeCode));
		spec = spec.and(StepBaseSpecs.withStepNo(stepNo));
		spec = spec.and(StepBaseSpecs.withUseYn(useYn));
		
		return stepBaseRepository.findAll(spec, pageable).map(i -> modelMapper.map(i, StepBaseDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewStepBase (StepBaseRequestDto dto) {
		stepBaseRepository.save(modelMapper.map(dto, StepBase.class));
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateStepBase (long id, StepBaseRequestDto dto) {
		StepBase stepBase = stepBaseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		stepBase.update(dto);
	}
	
	// 중복 체크
	public boolean checkOverlapping (String gradeCode, String stepNo, LocalDate fromDate, LocalDate toDate) {
		return stepBaseRepository.existsOverlap(gradeCode, stepNo, fromDate, toDate);
	}
	
	// 중복 체크(수정용)
	public boolean checkOverlapping (String gradeCode, String stepNo, LocalDate fromDate, LocalDate toDate, long id) {
		return stepBaseRepository.existsOverlapForUpdate(gradeCode, stepNo, fromDate, toDate, id);
	}
	
	// 세부 정보 조회
	public Optional<StepBaseDetailDto> findById (long id) {
		return stepBaseRepository.findById(id).map(i -> modelMapper.map(i, StepBaseDetailDto.class));
	}

}
