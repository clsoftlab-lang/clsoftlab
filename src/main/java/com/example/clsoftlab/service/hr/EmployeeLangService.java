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
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.EmployeeLangDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeLangRequestDto;
import com.example.clsoftlab.dto.hr.EmployeeLangSimpleDto;
import com.example.clsoftlab.entity.EmployeeLang;
import com.example.clsoftlab.repository.hr.EmployeeLangRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeLangService {

	private final EmployeeLangRepository employeeLangRepository;
	private final ModelMapper modelMapper;
	
	// 사번으로 page 조회
	public Page<EmployeeLangDetailDto> searchEmployeeLang (String pernr, int page , int size) {
		Pageable pageable = PageRequest.of(page, size);
		
		return employeeLangRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EmployeeLangDetailDto.class));
	}
	
	// 사번으로 simple List 조회
	public List<EmployeeLangSimpleDto> getSimpleList (String pernr) {
		return employeeLangRepository.findAllByPernrOrderBySeq(pernr).stream()
				.map(i -> modelMapper.map(i, EmployeeLangSimpleDto.class))
				.toList();
	}
	
	// 새 어학 리스트 저장
	@Transactional
	public void saveEmployeeLangList (String pernr, List<EmployeeLangRequestDto> newDtoList) {
		
		Set<Integer> langSeqs = new HashSet<>();
		
		List<EmployeeLang> originalLang = employeeLangRepository.findAllByPernrOrderBySeq(pernr);
		
		// 순차 검사
		for (EmployeeLangRequestDto dto : newDtoList) {
			if (!langSeqs.add(dto.getSeq())) {
				throw new IllegalArgumentException("순번 '" + dto.getSeq() + "'이(가) 중복으로 입력되었습니다.");
			}
		}
		
		Map<Long, EmployeeLang> originalMap = originalLang.stream()
				.collect(Collectors.toMap(EmployeeLang::getId, lang -> lang));
		
		Map<Long, EmployeeLangRequestDto> dtoMap = newDtoList.stream()
				.filter(dto -> dto.getId() != null)
				.collect(Collectors.toMap(EmployeeLangRequestDto::getId, dto -> dto));
		
		try {
			// 삭제 대상 정리 (원본 리스트에서 없어진 것들)
			List<EmployeeLang> langToDelete = originalLang.stream()
					.filter(lang -> !dtoMap.containsKey(lang.getId()))
					.toList();
			
			if (!langToDelete.isEmpty()) {
				employeeLangRepository.deleteAllInBatch(langToDelete);
			}
			
			for (EmployeeLangRequestDto dto : newDtoList) {
				
				// ID가 없으면 신규 등록
				if (dto.getId() == null) {
					employeeLangRepository.save(modelMapper.map(dto, EmployeeLang.class));
				} else { // ID가 있으면 수정
					EmployeeLang employeeLang = originalMap.get(dto.getId());
					if (employeeLang != null) {
						employeeLang.update(dto);
						employeeLangRepository.saveAndFlush(employeeLang);
					}
				}
			}
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("순번이 중복되었습니다.");
		}
	}
	
	
}
