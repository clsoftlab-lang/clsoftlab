package com.example.clsoftlab.service.hr;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.UnionMemberDetailDto;
import com.example.clsoftlab.dto.hr.UnionMemberRequestDto;
import com.example.clsoftlab.entity.UnionMember;
import com.example.clsoftlab.repository.hr.UnionMemberRepository;

import jakarta.transaction.Transactional;

@Service
public class UnionMemberService {

	private final UnionMemberRepository unionMemberRepository;
	private final ModelMapper modelMapper;
	
	public UnionMemberService(UnionMemberRepository unionMemberRepository, ModelMapper modelMapper) {
		this.unionMemberRepository = unionMemberRepository;
		this.modelMapper = modelMapper;
	}
	
	// 사번으로 page 조회
	public Page<UnionMemberDetailDto> findByPernr (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("joinDate").ascending());
		
		return unionMemberRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, UnionMemberDetailDto.class));
	}
	
	// 노조 가입 목록 저장
	@Transactional
	public void saveMemberList (String pernr, List<UnionMemberRequestDto> dtoList) {
		Set<String> unionTypeSet = new HashSet<>();
		
		// 중복 가입 검사
		for (UnionMemberRequestDto dto : dtoList) {
			if (dto.getUnionYn().equals("Y")) {
				if (!unionTypeSet.add(dto.getUnionType())) {
					throw new IllegalArgumentException("중복 가입 한 노조입니다."+ dto.getUnionType());
				}
			}
		}
		
		List<UnionMember> originalList = unionMemberRepository.findByPernr(pernr);
		
		Map<Long, UnionMember> originalMap = originalList.stream()
				.collect(Collectors.toMap(UnionMember::getId, member -> member));
		
		Map<Long, UnionMemberRequestDto> dtoMap = dtoList.stream()
				.filter(dto -> dto.getId() != null)
				.collect(Collectors.toMap(UnionMemberRequestDto::getId, dto -> dto));
		
		try {
			// 삭제 리스트
			List<UnionMember> memberToDelete = originalList.stream()
					.filter(member -> !dtoMap.containsKey(member.getId()))
					.toList();
			
			if (!memberToDelete.isEmpty()) {
				unionMemberRepository.deleteAllInBatch(memberToDelete);
			}
			
			for (UnionMemberRequestDto dto : dtoList) {
				if (dto.getId() == null) {
					unionMemberRepository.save(modelMapper.map(dto, UnionMember.class));
				} else {
					UnionMember unionMember = originalMap.get(dto.getId());
					if (unionMember != null) {
						unionMember.update(dto);
						unionMemberRepository.saveAndFlush(unionMember);
					}
				}
			}
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("순번이 중복되었습니다.");
		}
	}
}
