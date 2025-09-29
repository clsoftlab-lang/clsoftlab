package com.example.clsoftlab.service.hr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.OrgUnitFlatDto;
import com.example.clsoftlab.dto.hr.OrgUnitTreeDto;
import com.example.clsoftlab.entity.OrgUnit;
import com.example.clsoftlab.repository.hr.OrgUnitRepository;
import com.example.clsoftlab.repository.hr.specification.OrgUnitSpecs;

@Service
public class OrgUnitService {

	private final OrgUnitRepository orgUnitRepository;
	private final ModelMapper modelMapper;
	
	public OrgUnitService(OrgUnitRepository orgUnitRepository, ModelMapper modelMapper) {
		this.orgUnitRepository = orgUnitRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 목록 조회
	public List<OrgUnitTreeDto> getOrgUnitTree (String bizCode, String orgName, String useYn) {
		
		Specification<OrgUnit> spec = Specification.not(null);
		spec = spec.and(OrgUnitSpecs.withBizCode(bizCode))
				.and(OrgUnitSpecs.withOrgName(orgName))
				.and(OrgUnitSpecs.withUseYn(useYn));
		
		List<OrgUnit> orgUnit = orgUnitRepository.findAll(spec);
		
		List<OrgUnitFlatDto> flatList = orgUnit.stream()
				.map(i -> modelMapper.map(i, OrgUnitFlatDto.class))
				.toList();
		
		if (flatList.isEmpty()) {
			return new ArrayList<>();
		}
		
		Map<String, OrgUnitTreeDto> nodeMap = new HashMap<>();
		List<OrgUnitTreeDto> rootNodes = new ArrayList<>();
		
		for (OrgUnitFlatDto dto : flatList) {
	        OrgUnitTreeDto treeDto = new OrgUnitTreeDto(dto);
	        nodeMap.put(treeDto.getOrgCode(), treeDto);
	    }
		
		for (OrgUnitFlatDto dto : flatList) {
			String parentCode = dto.getParentOrgCode();
			OrgUnitTreeDto currentNode = nodeMap.get(dto.getOrgCode());
			
			if (parentCode != null) {
				OrgUnitTreeDto parentNode = nodeMap.get(parentCode);
				if (parentNode != null) {
					parentNode.getChildren().add(currentNode);
				}
			} else {
				rootNodes.add(currentNode);
			}
			
		}
		return rootNodes;
	}
}
