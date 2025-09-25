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

import com.example.clsoftlab.dto.pay.EmployeeClubDetailDto;
import com.example.clsoftlab.dto.pay.EmployeeClubRequestDto;
import com.example.clsoftlab.dto.pay.EmployeeClubSearchRequestDto;
import com.example.clsoftlab.entity.EmployeeClub;
import com.example.clsoftlab.repository.pay.ClubItemRepository;
import com.example.clsoftlab.repository.pay.EmployeeClubRepository;
import com.example.clsoftlab.repository.pay.specification.EmployeeClubSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmployeeClubService {

	private final EmployeeClubRepository employeeClubRepository;
	private final ClubItemRepository clubItemRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeClubService(EmployeeClubRepository employeeClubRepository, ClubItemRepository clubItemRepository, 
			ModelMapper modelMapper) {
		this.employeeClubRepository = employeeClubRepository;
		this.clubItemRepository = clubItemRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<EmployeeClubDetailDto> searchEmployeeClub (EmployeeClubSearchRequestDto search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("empNo"));
		
		Specification<EmployeeClub> spec = Specification.not(null);
		spec = spec.and(EmployeeClubSpecs.withEmpNo(search.getEmpNo()));
		spec = spec.and(EmployeeClubSpecs.withClubCode(search.getClubCode()));
		spec = spec.and(EmployeeClubSpecs.lessThanOrEqualToFromDate(search.getFromDate()));
		spec = spec.and(EmployeeClubSpecs.greaterThanOrEqualToToDate(search.getToDate()));
		
		return employeeClubRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, EmployeeClubDetailDto.class));
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewEmployeeClub (EmployeeClubRequestDto dto) {
		EmployeeClub employeeClub = modelMapper.map(dto, EmployeeClub.class);
		employeeClub.setClubItem(clubItemRepository.findById(dto.getClubCode())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. clubCode : " + dto.getClubCode())));
		
		employeeClubRepository.save(employeeClub);
	}
	
	// 기존 항목 수정
	@Transactional
	public void updateEmployeeClub (long id, EmployeeClubRequestDto dto) {
		EmployeeClub employeeClub = employeeClubRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		employeeClub.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String empNo, String clubCode, LocalDate fromDate, LocalDate toDate) {
		return employeeClubRepository.checkOverlap(empNo, clubCode, fromDate, toDate);
	}
	
	// 중복 검사 (수정용)
	public boolean checkOverlap (String empNo, String clubCode, LocalDate fromDate, LocalDate toDate, long id) {
		return employeeClubRepository.checkOverlap(empNo, clubCode, fromDate, toDate, id);
	}
	
	// 상세 정보 조회
	public Optional<EmployeeClubDetailDto> findById (long id) {
		return employeeClubRepository.findById(id)
				.map(i -> modelMapper.map(i, EmployeeClubDetailDto.class));
	}
}
