package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.PayCertificateDetailDto;
import com.example.clsoftlab.dto.pay.PayCertificateRequestDto;
import com.example.clsoftlab.entity.PayCertificate;
import com.example.clsoftlab.repository.pay.PayCertificateRepository;

import jakarta.transaction.Transactional;

@Service
public class PayCertificateService {

	private final PayCertificateRepository payCertificateRepository;
	private final ModelMapper modelMapper;
	
	public PayCertificateService(PayCertificateRepository payCertificateRepository, ModelMapper modelMapper) {
		this.payCertificateRepository = payCertificateRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<PayCertificateDetailDto> searchPayCertificate (String empNo, String year, String periodType, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("empNo"));
		
		return payCertificateRepository.findByYearContainingAndEmpNoContainingAndPeriodTypeContaining(year, empNo, periodType, pageable)
				.map(i -> modelMapper.map(i, PayCertificateDetailDto.class));
	}
	
	// 새 명세표 등록
	@Transactional
	public void addNewPayCertificate (PayCertificateRequestDto dto) {
		PayCertificate payCertificate = modelMapper.map(dto, PayCertificate.class);
		payCertificate.setCertNo(payCertificate.getYear()+payCertificate.getEmpNo());
		
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
}
