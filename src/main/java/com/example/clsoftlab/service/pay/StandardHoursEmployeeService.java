package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.StandardHoursEmployeeDetailDto;
import com.example.clsoftlab.dto.pay.StandardHoursEmployeeRequestDto;
import com.example.clsoftlab.entity.StandardHoursEmployee;
import com.example.clsoftlab.repository.pay.StandardHoursEmployeeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class StandardHoursEmployeeService {

	private final StandardHoursEmployeeRepository standardHoursEmployeeRepository;
	private final ModelMapper modelMapper;
	
	public StandardHoursEmployeeService(StandardHoursEmployeeRepository standardHoursEmployeeRepository ,
			ModelMapper modelMapper) {
		this.standardHoursEmployeeRepository = standardHoursEmployeeRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<StandardHoursEmployeeDetailDto> searchStandardHoursEmployee (String calYm, String empNo, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("calYm").descending());
		
		return standardHoursEmployeeRepository.searchStandardHoursEmployee(calYm, empNo, pageable)
				.map(i -> modelMapper.map(i, StandardHoursEmployeeDetailDto.class));
	}
	
	// 새 근로시간 등록
	@Transactional
	public void addNewStandardHoursEmployee (StandardHoursEmployeeRequestDto dto) {
		standardHoursEmployeeRepository.save(modelMapper.map(dto, StandardHoursEmployee.class));
	}
	
	// 근로시간 수정
	@Transactional
	public void updateStandardHoursEmployee (long id, StandardHoursEmployeeRequestDto dto) {
		StandardHoursEmployee standardHoursEmployee = standardHoursEmployeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 사원별 월 소정 근로시간을  찾을 수 없습니다. id : "+ id));
		standardHoursEmployee.update(dto);
	}
	
	// 중복 검사
	public long checkOverlappingStandardHoursEmployee (String calYm, String empNo) {
		return standardHoursEmployeeRepository.countByCalYmAndEmpNo(calYm, empNo);
	}
	
	// 디테일 정보
	public Optional<StandardHoursEmployeeDetailDto> findById (long id) {
		return standardHoursEmployeeRepository.findById(id)
				.map(i -> modelMapper.map(i, StandardHoursEmployeeDetailDto.class));
	}
}
