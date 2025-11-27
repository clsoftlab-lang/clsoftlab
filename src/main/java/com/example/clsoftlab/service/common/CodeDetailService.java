package com.example.clsoftlab.service.common;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.CodeDetailSearchDto;
import com.example.clsoftlab.repository.common.CodeDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodeDetailService {

	private final CodeDetailRepository codeDetailRepository;
	private final ModelMapper modelMapper;
	
	// 그룹 id로 useYn = 'Y'인 리스트 조회 (검색용)
	public List<CodeDetailSearchDto> findActiveCodesByGroupId (String groupId) {
		return codeDetailRepository.findAllByGroupIdAndUseYnOrderByOrder(groupId, "Y").stream()
				.map(i -> modelMapper.map(i, CodeDetailSearchDto.class))
				.toList();
	}
}
