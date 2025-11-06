package com.example.clsoftlab.service.pay;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.pay.ClubItemDetailDto;
import com.example.clsoftlab.dto.pay.ClubItemRequestDto;
import com.example.clsoftlab.dto.pay.ClubItemSearchDto;
import com.example.clsoftlab.entity.ClubItem;
import com.example.clsoftlab.repository.pay.ClubItemRepository;
import com.example.clsoftlab.repository.pay.specification.ClubItemSpecs;

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
	public Page<ClubItemDetailDto> searchClubItem (List<String> clubCode, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<ClubItem> spec = Specification.not(null);
		
		spec = spec.and(ClubItemSpecs.withClubCode(clubCode))
				.and(ClubItemSpecs.withUseYn(useYn));
		
		
		return clubItemRepository.findAll(spec, pageable)
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
		ClubItem clubItem = clubItemRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
		
		clubItem.update(dto);
	}
	
	// 코드 중복 검사
	public boolean checkOverlap (String clubCode) {
		return clubItemRepository.existsByClubCode(clubCode);
	}
	
	// 상세 정보 조회
	public Optional<ClubItemDetailDto> findById (Long id) {
		return clubItemRepository.findById(id)
				.map(i -> modelMapper.map(i, ClubItemDetailDto.class));
	}
	
	// 검색용 clubItem list 조회
	public List<ClubItemSearchDto> getClubItemList () {
		return clubItemRepository.findAllByOrderByClubName().stream()
				.map(i -> modelMapper.map(i, ClubItemSearchDto.class))
				.toList();
	}
}
