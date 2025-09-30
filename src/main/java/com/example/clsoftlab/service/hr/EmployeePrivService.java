package com.example.clsoftlab.service.hr;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.EmployeePrivDetailDto;
import com.example.clsoftlab.dto.hr.EmployeePrivRequestDto;
import com.example.clsoftlab.entity.EmployeePriv;
import com.example.clsoftlab.repository.hr.EmployeePrivRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeePrivService {

	private final EmployeePrivRepository employeePriveRepository;
	private final ModelMapper modelMapper;
	
	public EmployeePrivService(EmployeePrivRepository employeePriveRepository, ModelMapper modelMapper) {
		this.employeePriveRepository = employeePriveRepository;
		this.modelMapper = modelMapper;
	}
	
	// 상세 정보 조회
	public Optional<EmployeePrivDetailDto> findById (String pernr) {
		return employeePriveRepository.findById(pernr)
				.map(i-> modelMapper.map(i, EmployeePrivDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewEmployeePriv (EmployeePrivRequestDto dto) {
		if (employeePriveRepository.existsById(dto.getPernr())) {
			throw new IllegalStateException("이미 존재하는 사원번호 입니다. pernr : " + dto.getPernr());
		}
		
		employeePriveRepository.save(modelMapper.map(dto, EmployeePriv.class));
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateEmployeePriv (EmployeePrivRequestDto dto) {
		EmployeePriv employeePriv = employeePriveRepository.findById(dto.getPernr())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. pernr : " + dto.getPernr()));
		
		employeePriv.update(dto);
	}
	
	// 사번 중복 체크
	public boolean checkOverlap (String pernr) {
		return employeePriveRepository.existsById(pernr);
	}
}
