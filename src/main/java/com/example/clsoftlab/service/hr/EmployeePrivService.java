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
import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.EmployeePriv;
import com.example.clsoftlab.entity.EmployeePrivLog;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.hr.EmployeePrivLogRepository;
import com.example.clsoftlab.repository.hr.EmployeePrivRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeePrivService {

	private final EmployeePrivRepository employeePrivRepository;
	private final EmployeePrivLogRepository employeePrivLogRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	
	// 상세 정보 조회
	public Optional<EmployeePrivDetailDto> findById (String pernr) {
		return employeePrivRepository.findById(pernr)
				.map(priv -> {
					EmployeePrivDetailDto dto = modelMapper.map(priv, EmployeePrivDetailDto.class);
					
					EmployeeMaster master = priv.getEmployee();
					if (master != null) {
						dto.setName(master.getName());
						dto.setDeptCode(master.getDeptCode());
						dto.setRankCode(master.getRankCode());
						dto.setDutyCode(master.getDutyCode());
						dto.setEntryDate(master.getEntryDate());
						dto.setEmpStatus(master.getEmpStatus());
					}
					
					return dto;
				});
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewEmployeePriv (EmployeePrivRequestDto dto) {
		if (employeePrivRepository.existsById(dto.getPernr())) {
			throw new IllegalStateException("이미 존재하는 사원번호 입니다. pernr : " + dto.getPernr());
		}
		
		EmployeeMaster employeeMaster = employeeMasterRepository.findById(dto.getPernr())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. pernr : " + dto.getPernr()));
		
		EmployeePriv employeePriv = modelMapper.map(dto, EmployeePriv.class);
		
		employeePriv.setEmployee(employeeMaster);
		
		employeePrivRepository.save(employeePriv);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateEmployeePriv (EmployeePrivRequestDto dto) {
		EmployeePriv employeePriv = employeePrivRepository.findById(dto.getPernr())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. pernr : " + dto.getPernr()));
		
		List<EmployeePrivLog> logs = new ArrayList<>();
		
		logIfChanged(logs, employeePriv, "gender", employeePriv.getGender(), dto.getGender());
		logIfChanged(logs, employeePriv, "birthDate", employeePriv.getBirthDate(), dto.getBirthDate());
		logIfChanged(logs, employeePriv, "nationCode", employeePriv.getNationCode(), dto.getNationCode());
		
		logIfChanged(logs, employeePriv, "phoneNo", employeePriv.getPhoneNo(), dto.getPhoneNo());
		logIfChanged(logs, employeePriv, "homeTel", employeePriv.getHomeTel(), dto.getHomeTel());
		logIfChanged(logs, employeePriv, "email", employeePriv.getEmail(), dto.getEmail());
		
		// 주소 필드 로그
		logIfChanged(logs, employeePriv, "postCode", employeePriv.getPostCode(), dto.getPostCode());
		logIfChanged(logs, employeePriv, "addrMain", employeePriv.getAddrMain(), dto.getAddrMain());
		logIfChanged(logs, employeePriv, "addrDetail", employeePriv.getAddrDetail(), dto.getAddrDetail());
		
		// 공통코드 필드 로그
		logIfChanged(logs, employeePriv, "maritalCode", employeePriv.getMaritalCode(), dto.getMaritalCode());
		logIfChanged(logs, employeePriv, "militaryCode", employeePriv.getMilitaryCode(), dto.getMilitaryCode());
		logIfChanged(logs, employeePriv, "disabilityYn", employeePriv.getDisabilityYn(), dto.getDisabilityYn());
		
		logIfChanged(logs, employeePriv, "emergencyName", employeePriv.getEmergencyName(), dto.getEmergencyName());
		logIfChanged(logs, employeePriv, "emergencyRel", employeePriv.getEmergencyRel(), dto.getEmergencyRel());
		logIfChanged(logs, employeePriv, "emergencyPhone", employeePriv.getEmergencyPhone(), dto.getEmergencyPhone());
		
		employeePriv.update(dto);
		
		if (!logs.isEmpty()) {
	        employeePrivLogRepository.saveAll(logs);
	    }
	}
	
	// 사번 중복 체크
	public boolean checkOverlap (String pernr) {
		return employeePrivRepository.existsById(pernr);
	}
	
	//  비교 및 로그 생성을 처리하는 헬퍼 메서드
	private void logIfChanged(List<EmployeePrivLog> logs, EmployeePriv entity, String fieldName, 
	                          Object oldValue, Object newValue) {
	    
		if (!Objects.equals(String.valueOf(oldValue), String.valueOf(newValue))) {
	        logs.add(new EmployeePrivLog(
	                null,
	                entity.getPernr(),
	                fieldName,
	                oldValue != null ? oldValue.toString() : null,
	                newValue != null ? newValue.toString() : null,
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
