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

import com.example.clsoftlab.dto.pay.StandardHoursDetailDto;
import com.example.clsoftlab.dto.pay.StandardHoursRequestDto;
import com.example.clsoftlab.dto.pay.StandardHoursSearchDto;
import com.example.clsoftlab.entity.StandardHours;
import com.example.clsoftlab.repository.pay.StandardHoursRepository;
import com.example.clsoftlab.repository.pay.specification.StandardHoursSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class StandardHoursService {

	private final StandardHoursRepository standardHoursRepository;
	private final ModelMapper modelMapper;
	
	public StandardHoursService(StandardHoursRepository standardHoursRepository, ModelMapper modelMapper) {
		this.standardHoursRepository = standardHoursRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색으로 조회
	public Page<StandardHoursDetailDto> searchStandardHours (String calYm, List<String> jobGroup, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("calYm").descending());
		Specification<StandardHours> spec = Specification.not(null);
		
		spec = spec.and(StandardHoursSpecs.withCalYm(calYm))
				.and(StandardHoursSpecs.withJobGroup(jobGroup));
		
		return standardHoursRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, StandardHoursDetailDto.class));
	}
	
	// 새 근로시간 등록
	@Transactional
	public void addNewStandardHours (StandardHoursRequestDto dto) {
		standardHoursRepository.save(modelMapper.map(dto, StandardHours.class));
	}
	
	// 근로 시간 수정
	@Transactional
	public void updateStandardHours (long id, StandardHoursRequestDto dto) {
		
		StandardHours standardHours = standardHoursRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 월별 소정 근로시간을 찾을 수 없습니다. id : " + id));
		
		standardHours.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlappingStandardHours (String calYm, String jobGroup) {
	    	return standardHoursRepository.existsByCalYmAndJobGroup(calYm, jobGroup);
	}
	
	// 세부 정보 찾기
	public Optional<StandardHoursDetailDto> findById (long id) {
		return standardHoursRepository.findById(id).map(i -> modelMapper.map(i, StandardHoursDetailDto.class));
	}
	
	// jobGroup list 조회
	public List<StandardHoursSearchDto> getJobGroupList () {
		return standardHoursRepository.getJobGroupList();
	}
}
