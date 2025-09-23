package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.BankAccountDetailDto;
import com.example.clsoftlab.dto.pay.BankAccountDto;
import com.example.clsoftlab.dto.pay.BankAccountRequestDto;
import com.example.clsoftlab.entity.BankAccount;
import com.example.clsoftlab.repository.pay.BankAccountRepository;
import com.example.clsoftlab.util.HmacSha256Util;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BankAccountService {
	
	@Value("${encrypt.hmac.key}") // application.yml에 저장된 해시용 비밀 키
	private String hmacKey;

	private final BankAccountRepository bankAccountRepository;
	private final ModelMapper modelMapper;
	
	public BankAccountService(BankAccountRepository bankAccountRepository, ModelMapper modelMapper) {
		this.bankAccountRepository = bankAccountRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<BankAccountDetailDto> searchBankAccount (String empNo, String accountType, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("empNo"));
		
		return bankAccountRepository.findByEmpNoContainingAndAccountTypeContainingAndUseYnContaining(empNo, accountType, useYn, pageable)
				.map(i -> modelMapper.map(i, BankAccountDetailDto.class));
	}
	
	// 새 계좌 등록
	@Transactional
	public void addNewBankAccount (BankAccountRequestDto dto) {
		BankAccount bankAccount = modelMapper.map(dto, BankAccount.class);
		bankAccount.setAccountNoHash(HmacSha256Util.generateHmacSha256(dto.getAccountNo(), hmacKey));
		bankAccountRepository.save(bankAccount);
	}
	
	// 특정 계좌 수정
	@Transactional
	public void updateBankAccount (long id, BankAccountRequestDto dto) {
		BankAccount bankAccount = bankAccountRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		bankAccount.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String empNo, String accountType, String accountNo, LocalDate fromDate, LocalDate toDate) {
		String accountNoHash = HmacSha256Util.generateHmacSha256(accountNo, hmacKey);
		return bankAccountRepository.checkOverlap(empNo, accountType, accountNoHash, fromDate, toDate);
	}
	
	// 중복 검사 (수정용)
	public boolean checkOverlap (LocalDate fromDate, LocalDate toDate, long id) {
		
		BankAccountDto bankAccount = bankAccountRepository.findById(id)
				.map(i -> modelMapper.map(i, BankAccountDto.class))
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		String accountNoHash = HmacSha256Util.generateHmacSha256(bankAccount.getAccountNo(), hmacKey);
		return bankAccountRepository.checkOverlap(bankAccount.getEmpNo(), bankAccount.getAccountType(), accountNoHash, fromDate, toDate, id);
	}
	
	// 세부 정보 조회
	public Optional<BankAccountDetailDto> findById (long id) {
		return bankAccountRepository.findById(id)
				.map(i -> modelMapper.map(i, BankAccountDetailDto.class));
	}
}
