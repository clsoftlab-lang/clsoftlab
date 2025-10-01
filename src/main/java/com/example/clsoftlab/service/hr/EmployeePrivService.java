package com.example.clsoftlab.service.hr;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.EmployeePrivDetailDto;
import com.example.clsoftlab.dto.hr.EmployeePrivLogDetailDto;
import com.example.clsoftlab.dto.hr.EmployeePrivRequestDto;
import com.example.clsoftlab.entity.EmployeePriv;
import com.example.clsoftlab.entity.EmployeePrivLog;
import com.example.clsoftlab.repository.hr.EmployeePrivLogRepository;
import com.example.clsoftlab.repository.hr.EmployeePrivRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeePrivService {

	private final EmployeePrivRepository employeePriveRepository;
	private final EmployeePrivLogRepository employeePrivLogRepository;
	private final ModelMapper modelMapper;
	
	public EmployeePrivService(EmployeePrivRepository employeePriveRepository, EmployeePrivLogRepository employeePrivLogRepository,
			ModelMapper modelMapper) {
		this.employeePriveRepository = employeePriveRepository;
		this.employeePrivLogRepository = employeePrivLogRepository;
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
		
		List<EmployeePrivLog> logs = new ArrayList<>();
		
		logIfChanged(logs, employeePriv, "name", employeePriv.getName(), dto.getName(), null);
		logIfChanged(logs, employeePriv, "gender", employeePriv.getGender(), dto.getGender(), null);
		logIfChanged(logs, employeePriv, "birthdate", employeePriv.getBirthdate(), dto.getBirthdate(), null);
		logIfChanged(logs, employeePriv, "nationality", employeePriv.getNationality(), dto.getNationality(), null);
		logIfChanged(logs, employeePriv, "phoneNo", employeePriv.getPhoneNo(), dto.getPhoneNo(), null);
		logIfChanged(logs, employeePriv, "email", employeePriv.getEmail(), dto.getEmail(), null);
		logIfChanged(logs, employeePriv, "email", employeePriv.getEmail(), dto.getEmail(), null);
		logIfChanged(logs, employeePriv, "addr", employeePriv.getAddr(), dto.getAddr(), null);
		logIfChanged(logs, employeePriv, "addrDetail", employeePriv.getAddrDetail(), dto.getAddrDetail(), null);
		logIfChanged(logs, employeePriv, "martialStatus", employeePriv.getMaritalStatus(), dto.getMaritalStatus(), null);
		logIfChanged(logs, employeePriv, "militaryInfo", employeePriv.getMilitaryInfo(), dto.getMilitaryInfo(), null);
		logIfChanged(logs, employeePriv, "disabilityYn", employeePriv.getDisabilityYn(), dto.getDisabilityYn(), null);
		logIfChanged(logs, employeePriv, "emergencyName", employeePriv.getEmergencyName(), dto.getEmergencyName(), null);
		logIfChanged(logs, employeePriv, "emergencyRel", employeePriv.getEmergencyRel(), dto.getEmergencyRel(), null);
		logIfChanged(logs, employeePriv, "emergencyPhone", employeePriv.getEmergencyPhone(), dto.getEmergencyPhone(), null);
		
		employeePriv.update(dto);
		
		if (!logs.isEmpty()) {
	        employeePrivLogRepository.saveAll(logs);
	    }
	}
	
	// 사번 중복 체크
	public boolean checkOverlap (String pernr) {
		return employeePriveRepository.existsById(pernr);
	}
	
	//  비교 및 로그 생성을 처리하는 헬퍼 메서드
	private void logIfChanged(List<EmployeePrivLog> logs, EmployeePriv entity, String fieldName, 
	                          Object oldValue, Object newValue, String updaterId) {
	    
	    // null을 안전하게 비교하기 위해 Objects.equals 사용
	    if (!Objects.equals(oldValue, newValue)) {
	        logs.add(new EmployeePrivLog(
	                null,
	                entity.getPernr(),
	                fieldName,
	                String.valueOf(oldValue), // oldValue를 문자열로 변환
	                String.valueOf(newValue), // newValue를 문자열로 변환
	                "UPDATE"
	        ));
	    }
	}
	
	// 로그 조회
	public Page<EmployeePrivLogDetailDto> findByPernr (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		return employeePrivLogRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EmployeePrivLogDetailDto.class));
	}
}
