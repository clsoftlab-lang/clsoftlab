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
import com.example.clsoftlab.dto.pay.PayCertificateDetailDto;
import com.example.clsoftlab.dto.pay.PayCertificateRequestDto;
import com.example.clsoftlab.entity.PayCertificate;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.PayCertificateRepository;
import com.example.clsoftlab.repository.pay.specification.PayCertificateSpecs;

import jakarta.transaction.Transactional;

@Service
public class PayCertificateService {

	private final PayCertificateRepository payCertificateRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public PayCertificateService(PayCertificateRepository payCertificateRepository, EmployeeMasterRepository employeeMasterRepository,
			ModelMapper modelMapper) {
		this.payCertificateRepository = payCertificateRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<PayCertificateDetailDto> searchPayCertificate (List<String> empNo, String year, List<String> periodType, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<PayCertificate> spec = Specification.not(null);
		
		spec = spec.and(PayCertificateSpecs.withEmpNo(empNo))
				.and(PayCertificateSpecs.withYear(year))
				.and(PayCertificateSpecs.withPeriodType(periodType));
		
		return payCertificateRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, PayCertificateDetailDto.class));
	}
	
	// 새 명세표 등록
	@Transactional
	public void addNewPayCertificate (PayCertificateRequestDto dto) {
		PayCertificate payCertificate = modelMapper.map(dto, PayCertificate.class);
		String certNo = String.format("%s-%s-%s-%s",
                dto.getYear(),
                dto.getEmpNo(),
                dto.getPeriodType(), // "QUARTER"
                dto.getPeriodFrom().toString() // "2025-01-01"
        );
        payCertificate.setCertNo(certNo);
		payCertificate.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		
		payCertificateRepository.save(payCertificate);
	}
	
	// 기간 중복 검사
	public boolean checkOverlap (String empNo, String year, String periodType, LocalDate periodFrom, LocalDate periodTo) {
		return payCertificateRepository.checkOverlap(empNo, year, periodType, periodFrom, periodTo);
	}
	
	// 상세 정보
	public Optional<PayCertificateDetailDto> findById (long id) {
		return payCertificateRepository.findById(id)
				.map(i -> modelMapper.map(i, PayCertificateDetailDto.class));
	}
	
	// 검색용 사원 list 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return payCertificateRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
}
