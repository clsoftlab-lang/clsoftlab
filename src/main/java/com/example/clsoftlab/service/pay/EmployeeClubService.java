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
import com.example.clsoftlab.dto.pay.ClubItemSearchDto;
import com.example.clsoftlab.dto.pay.EmployeeClubDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeClubRequestDto;
import com.example.clsoftlab.dto.pay.EmployeeClubSearchRequestDto;
import com.example.clsoftlab.entity.EmployeeClub;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.ClubItemRepository;
import com.example.clsoftlab.repository.pay.EmployeeClubRepository;
import com.example.clsoftlab.repository.pay.specification.EmployeeClubSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeClubService {

	private final EmployeeClubRepository employeeClubRepository;
	private final ClubItemRepository clubItemRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeClubService(EmployeeClubRepository employeeClubRepository, ClubItemRepository clubItemRepository, 
			EmployeeMasterRepository employeeMasterRepository, ModelMapper modelMapper) {
		this.employeeClubRepository = employeeClubRepository;
		this.clubItemRepository = clubItemRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<EmployeeClubDetailDto> searchEmployeeClub (EmployeeClubSearchRequestDto search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<EmployeeClub> spec = Specification.not(null);
		
		spec = spec.and(EmployeeClubSpecs.withClubCode(search.getClubCode()))
				.and(EmployeeClubSpecs.withEmpNo(search.getEmpNo()))
				.and(EmployeeClubSpecs.withPayYn(search.getPayYn()))
				.and(EmployeeClubSpecs.withDateRange(search.getFromDate(), search.getToDate()));
		
		return employeeClubRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, EmployeeClubDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewEmployeeClub (EmployeeClubRequestDto dto) {
		EmployeeClub employeeClub = modelMapper.map(dto, EmployeeClub.class);
		employeeClub.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		employeeClub.setClubItem(clubItemRepository.getReferenceById(dto.getClubId()));
		
		employeeClubRepository.save(employeeClub);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateEmployeeClub (EmployeeClubRequestDto dto) {
		EmployeeClub employeeClub = employeeClubRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		
		employeeClub.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String empNo, Long clubId, LocalDate fromDate, LocalDate toDate, Long id) {
		if (id == null) {
			return employeeClubRepository.checkOverlap(empNo, clubId, fromDate, toDate);
		} else {
			return employeeClubRepository.checkOverlap(empNo, clubId, fromDate, toDate, id);
		}
	}
	
	// 상세 정보 조회
	public Optional<EmployeeClubDetailDto> findById (long id) {
		return employeeClubRepository.findById(id)
				.map(i -> modelMapper.map(i, EmployeeClubDetailDto.class));
	}
	
	// 검색용 사원 리스트 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return employeeClubRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
	
	// 검색용 clubItem 리스트 조회
	public List<ClubItemSearchDto> getClubItemList () {
		return employeeClubRepository.getClubItemList().stream()
				.map(i -> modelMapper.map(i, ClubItemSearchDto.class))
				.toList();
	}
}
