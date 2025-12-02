package com.example.clsoftlab.service.hr;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.AppointHistDetailDto;
import com.example.clsoftlab.entity.AppointFieldDef;
import com.example.clsoftlab.entity.AppointHist;
import com.example.clsoftlab.repository.hr.AppointFieldDefRepository;
import com.example.clsoftlab.repository.hr.AppointHistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointHistService {

	private final AppointHistRepository appointHistRepository;
	private final AppointFieldDefRepository appointFieldDefRepository;
	private final ModelMapper modelMapper;
	
	// 사번으로 내역 리스트 조회
	public List<AppointHistDetailDto> findByPernr(String pernr) {
        List<AppointHist> histList = appointHistRepository.findAllByEmployee_PernrAndIsCanceledOrderByEffDateDesc(pernr, "N");

        List<AppointHistDetailDto> dtoList = histList.stream()
                .map(hist -> modelMapper.map(hist, AppointHistDetailDto.class))
                .toList();

        if (dtoList.isEmpty()) { 
        	return dtoList;
        }

        //조회된 이력들에 포함된 모든 RuleType 수집 (중복 제거)
        Set<String> ruleTypes = dtoList.stream()
                .map(AppointHistDetailDto::getRuleType)
                .collect(Collectors.toSet());

        // 해당 RuleType들에 정의된 FieldDef 목록 조회
        List<AppointFieldDef> fieldDefs = appointFieldDefRepository.findByRuleTypeIn(ruleTypes);

        Map<String, String> fieldNameMap = fieldDefs.stream()
                .collect(Collectors.toMap(
                        def -> def.getRuleType() + "_" + def.getFieldCode(),
                        AppointFieldDef::getFieldName,
                        (existing, replacement) -> existing // 혹시 모를 키 중복 방지
                ));

        //  DTO 순회하며 이름 채워넣기
        for (AppointHistDetailDto dto : dtoList) {
            String currentRuleType = dto.getRuleType();
            
            if (dto.getDetails() != null) {
                for (var detail : dto.getDetails()) {
                    String key = currentRuleType + "_" + detail.getFieldCode();
                    detail.setFieldName(fieldNameMap.getOrDefault(key, detail.getFieldCode()));
                }
            }
        }

        return dtoList;
    }
}
