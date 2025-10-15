package com.example.clsoftlab.service.hr;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.EmployeePersonalDetailDto;
import com.example.clsoftlab.dto.hr.EmployeePersonalRequestDto;
import com.example.clsoftlab.entity.EmployeePersonal;
import com.example.clsoftlab.repository.hr.EmployeePersonalRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeePersonalService {

	private final EmployeePersonalRepository employeePersonalRepository;
	private final ModelMapper modelMapper;
	
	public EmployeePersonalService(EmployeePersonalRepository employeePersonalRepository, ModelMapper modelMapper) {
		this.employeePersonalRepository = employeePersonalRepository;
		this.modelMapper = modelMapper;
	}
	
	// 사번으로 조회
	public Optional<EmployeePersonalDetailDto> findByPernr (String pernr) {
		return employeePersonalRepository.findById(pernr)
				.map(i -> modelMapper.map(i, EmployeePersonalDetailDto.class));
	}
	
	// 새 정보 등록
	@Transactional
	public void addNewPersonal (EmployeePersonalRequestDto dto) {
		employeePersonalRepository.save(modelMapper.map(dto, EmployeePersonal.class));
	}
	
	// 기존 정보 수정
	@Transactional
	public void updatePersonal (EmployeePersonalRequestDto dto) {
		EmployeePersonal employeePersonal = employeePersonalRepository.findById(dto.getPernr())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. pernr : " + dto.getPernr()));
		
		employeePersonal.update(dto);
	}
	
	// 기존 정보 삭제
	@Transactional
	public void deletePersonal (String pernr) {
		EmployeePersonal employeePersonal = employeePersonalRepository.findById(pernr)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. pernr : " + pernr));
		employeePersonalRepository.delete(employeePersonal);
	}
	
	// 사번 중복 검사
	public boolean checkOverlap (String pernr) {
		return employeePersonalRepository.existsById(pernr);
	}
}
