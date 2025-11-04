package com.example.clsoftlab.service.pay;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.EmployeeMasterDto;
import com.example.clsoftlab.dto.pay.BankAccountDetailDto;
import com.example.clsoftlab.dto.pay.BankAccountDto;
import com.example.clsoftlab.dto.pay.BankAccountRequestDto;
import com.example.clsoftlab.entity.BankAccount;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.pay.BankAccountRepository;
import com.example.clsoftlab.repository.pay.specification.BankAccountSpecs;
import com.example.clsoftlab.util.HmacSha256Util;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BankAccountService {
	
	@Value("${encrypt.hmac.key}") // application.yml에 저장된 해시용 비밀 키
	private String hmacKey;

	private final BankAccountRepository bankAccountRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final ModelMapper modelMapper;
	
	public BankAccountService(BankAccountRepository bankAccountRepository, EmployeeMasterRepository employeeMasterRepository,
			ModelMapper modelMapper) {
		this.bankAccountRepository = bankAccountRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 조회
	public Page<BankAccountDetailDto> searchBankAccount (List<String> empNo, List<String> accountType, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<BankAccount> spec = Specification.not(null);
		
		spec = spec.and(BankAccountSpecs.withEmpNo(empNo))
				.and(BankAccountSpecs.withAccountType(accountType))
				.and(BankAccountSpecs.withUseYn(useYn));
		
		return bankAccountRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, BankAccountDetailDto.class));
	}
	
	// 새 계좌 등록
	@Transactional
	public void addNewBankAccount (BankAccountRequestDto dto) {
		BankAccount bankAccount = modelMapper.map(dto, BankAccount.class);
		bankAccount.setAccountNoHash(HmacSha256Util.generateHmacSha256(dto.getAccountNo(), hmacKey));
		bankAccount.setEmployee(employeeMasterRepository.getReferenceById(dto.getEmpNo()));
		bankAccountRepository.save(bankAccount);
	}
	
	// 기존 계좌 수정
	@Transactional
	public void updateBankAccount (long id, BankAccountRequestDto dto) {
		BankAccount bankAccount = bankAccountRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
		
		bankAccount.update(dto);
	}
	
	// 중복 검사
	public boolean checkOverlap (String empNo, String accountType, String accountNo, LocalDate fromDate, LocalDate toDate, Long id) {
		String accountNoHash = HmacSha256Util.generateHmacSha256(accountNo, hmacKey);
		if (id == null) {
			return bankAccountRepository.checkOverlap(empNo, accountType, accountNoHash, fromDate, toDate);
		} else {
			BankAccountDto bankAccount = bankAccountRepository.findById(id)
					.map(i -> modelMapper.map(i, BankAccountDto.class))
					.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + id));
			
			accountNoHash = HmacSha256Util.generateHmacSha256(bankAccount.getAccountNo(), hmacKey);
			
			return bankAccountRepository.checkOverlap(empNo, accountType, accountNoHash, fromDate, toDate, id);
		}
	}
	
	// 세부 정보 조회
	public Optional<BankAccountDetailDto> findById (long id) {
		return bankAccountRepository.findById(id)
				.map(i -> modelMapper.map(i, BankAccountDetailDto.class));
	}
	
	// 검색용 사원 조회
	public List<EmployeeMasterDto> getEmployeeList () {
		return bankAccountRepository.getEmployeeList().stream()
				.map(i -> modelMapper.map(i, EmployeeMasterDto.class))
				.toList();
	}
}
