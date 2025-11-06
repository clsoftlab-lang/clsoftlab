package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.OvertimeDetailDto;
import com.example.clsoftlab.dto.pay.OvertimeDetailRequestDto;
import com.example.clsoftlab.entity.OvertimeDetail;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.OvertimeDetailRepository;
import com.example.clsoftlab.repository.pay.specification.OvertimeDetailSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OvertimeDetailService {

	private final OvertimeDetailRepository overtimeDetailRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public OvertimeDetailService(OvertimeDetailRepository overtimeDetailRepository, EmployeeMasterRepository employeeMasterRepository,
			ModelMapper modelMapper) {
		this.overtimeDetailRepository = overtimeDetailRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<OvertimeDetailDto> searchOvertimeDetail (List<String> empNo, LocalDate date, List<String> type, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<OvertimeDetail> spec = Specification.not(null);
		
		spec = spec.and(OvertimeDetailSpecs.withEmpNo(empNo))
				.and(OvertimeDetailSpecs.withDate(date))
				.and(OvertimeDetailSpecs.withType(type));
		
		return overtimeDetailRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, OvertimeDetailDto.class));
	}
	
	// 새 근로 실적 등록
	@Transactional
	public void addNewOvertimeDetail (OvertimeDetailRequestDto dto) {
		OvertimeDetail overtimeDetail = modelMapper.map(dto, OvertimeDetail.class);
		overtimeDetail.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		overtimeDetailRepository.save(overtimeDetail);
	}
	
	// 기존 근로 실적 수정
	@Transactional
	public void updateOvertimeDetail (OvertimeDetailRequestDto dto) {
		OvertimeDetail overtimeDetail = overtimeDetailRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		
		overtimeDetail.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String empNo, LocalDate date, String type, Long id) {
		if (id == null) {
			return overtimeDetailRepository.existsByEmployee_PernrAndDateAndType(empNo, date, type);
		} else {
			return overtimeDetailRepository.existsByEmployee_PernrAndDateAndTypeAndIdNot(empNo, date, type, id);
		}
	}
	
	
	// 세부 정부 조회
	public Optional<OvertimeDetailDto> findById (long id) {
		return overtimeDetailRepository.findById(id)
				.map(i -> modelMapper.map(i, OvertimeDetailDto.class));
	}
	
	// 검색용 사원 리스트 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return overtimeDetailRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
}
