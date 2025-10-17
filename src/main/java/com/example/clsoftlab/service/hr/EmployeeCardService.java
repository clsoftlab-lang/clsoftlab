package com.example.clsoftlab.service.hr;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.EmployeeCardDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeCardRequestDto;
import com.example.clsoftlab.entity.EmployeeCard;
import com.example.clsoftlab.repository.hr.EmployeeCardRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeCardService {

	private EmployeeCardRepository employeeCardRepository;
	private ModelMapper modelMapper;
	
	public EmployeeCardService(EmployeeCardRepository employeeCardRepository, ModelMapper modelMapper) {
		this.employeeCardRepository = employeeCardRepository;
		this.modelMapper = modelMapper;
	}
	
	// 사번으로 조회
	public Optional<EmployeeCardDetailDto> findByPernr (String pernr) {
		return employeeCardRepository.findById(pernr)
				.map(i -> modelMapper.map(i, EmployeeCardDetailDto.class));
	}
	
	// 인사 카드 등록/수정
	@Transactional
	public void saveCard (EmployeeCardRequestDto dto) {
		if (!employeeCardRepository.existsById(dto.getPernr())) {
			if (dto.getRrn() == null || dto.getRrn().isBlank()) {
		        throw new IllegalArgumentException("신규 등록 시 주민등록번호는 필수입니다.");
		    }
			employeeCardRepository.save(modelMapper.map(dto, EmployeeCard.class));
		} else {
			EmployeeCard employeeCard = employeeCardRepository.findById(dto.getPernr()).get();
			if (dto.getRrn() != null && !dto.getRrn().isBlank()) { // 주민번호 수정
	            employeeCard.setRrn(dto.getRrn());
	        }
			employeeCard.update(dto);
		}
	}
}
