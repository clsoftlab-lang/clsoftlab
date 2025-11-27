package com.example.clsoftlab.service.common;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.UserAccountRequestDto;
import com.example.clsoftlab.entity.UserAccount;
import com.example.clsoftlab.repository.common.UserAccountRepository;

import jakarta.transaction.Transactional;

@Service
public class UserAccountService {


	private final UserAccountRepository userAccountRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	public UserAccountService(UserAccountRepository userAccountRepository, ModelMapper modelMapper, 
			PasswordEncoder passwordEncoder) {
		this.userAccountRepository = userAccountRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	// 새 계정 생성
	@Transactional
	public void addNewAccount (UserAccountRequestDto dto) {
		if (userAccountRepository.existsById(dto.getUserId())) {
			throw new IllegalArgumentException("이미 사용 중인 아이디입니다: " + dto.getUserId());
		} 
		UserAccount account = modelMapper.map(dto, UserAccount.class);
		account.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		userAccountRepository.save(account);
	}
	
	
}
