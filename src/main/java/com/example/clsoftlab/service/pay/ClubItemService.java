package com.example.clsoftlab.service.pay;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.ClubItemDetailDto;
import com.example.clsoftlab.dto.pay.ClubItemRequestDto;
import com.example.clsoftlab.entity.ClubItem;
import com.example.clsoftlab.repository.pay.ClubItemRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ClubItemService {

	private final ClubItemRepository clubItemRepository;
	private final ModelMapper modelMapper;
	
	public ClubItemService(ClubItemRepository clubItemRepository, ModelMapper modelMapper) {
		this.clubItemRepository = clubItemRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 전체 목록 조회
	public Page<ClubItemDetailDto> searchClubItem (String clubCode, String clubName, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("clubCode"));
		
		return clubItemRepository.findByClubCodeContainingAndClubNameContainingAndUseYnContaining(clubCode, clubName, useYn, pageable)
				.map(i -> modelMapper.map(i, ClubItemDetailDto.class));
	}
	
	// 새 회비 항목 등록
	@Transactional
	public void addNewClubItem (ClubItemRequestDto dto) {
		clubItemRepository.save(modelMapper.map(dto, ClubItem.class));
	}
	
	// 기존 회비 항목 수정
	@Transactional
	public void updateClubItem (ClubItemRequestDto dto) {
		ClubItem clubItem = clubItemRepository.findById(dto.getClubCode())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. clubCode : " + dto.getClubCode()));
		
		clubItem.update(dto);
	}
	
	// 코드 중복 검사
	public boolean checkOverlap (String clubCode) {
		return clubItemRepository.existsByClubCode(clubCode);
	}
	
	// 상세 정보 조회
	public Optional<ClubItemDetailDto> findById (String clubCode) {
		return clubItemRepository.findById(clubCode)
				.map(i -> modelMapper.map(i, ClubItemDetailDto.class));
	}
}
