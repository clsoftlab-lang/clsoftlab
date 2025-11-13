package com.example.clsoftlab.service.hr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.controller.pay.GlMappingRuleController;
import com.example.clsoftlab.dto.hr.OrgUnitDetailDto;
import com.example.clsoftlab.dto.hr.OrgUnitExcelDto;
import com.example.clsoftlab.dto.hr.OrgUnitFlatDto;
import com.example.clsoftlab.dto.hr.OrgUnitRequestDto;
import com.example.clsoftlab.dto.hr.OrgUnitTreeDto;
import com.example.clsoftlab.entity.BizPlace;
import com.example.clsoftlab.entity.OrgUnit;
import com.example.clsoftlab.repository.common.EmployeeMasterRepository;
import com.example.clsoftlab.repository.hr.BizPlaceRepository;
import com.example.clsoftlab.repository.hr.OrgUnitRepository;
import com.example.clsoftlab.repository.hr.specification.OrgUnitSpecs;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrgUnitService {

	private final OrgUnitRepository orgUnitRepository;
	private final EmployeeMasterRepository employeeMasterRepository;
	private final BizPlaceRepository bizPlaceRepository;
	private final ModelMapper modelMapper;
	
	public OrgUnitService(OrgUnitRepository orgUnitRepository, EmployeeMasterRepository employeeMasterRepository,
			BizPlaceRepository bizPlaceRepository, ModelMapper modelMapper) {
		this.orgUnitRepository = orgUnitRepository;
		this.employeeMasterRepository = employeeMasterRepository;
		this.bizPlaceRepository = bizPlaceRepository;
		this.modelMapper = modelMapper;
	}
	
	// 검색어로 목록 조회 (tree)
	public List<OrgUnitTreeDto> getOrgUnitTree (List<String> bizCode, List<String> orgName, String useYn) {
		
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
	
	// 검색어로 목록 조회
	public Page<OrgUnitFlatDto> getOrgUnitList (List<String> bizCode, List<String> orgName, String useYn, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("orgName"));
		Specification<OrgUnit> spec = Specification.not(null);
		spec = spec.and(OrgUnitSpecs.withBizCode(bizCode))
				.and(OrgUnitSpecs.withOrgName(orgName))
				.and(OrgUnitSpecs.withUseYn(useYn));
		
		return orgUnitRepository.findAll(spec, pageable)
				.map(i -> modelMapper.map(i, OrgUnitFlatDto.class));
		
	}
	
	// 새 항목 등록
	@Transactional
	public void addNewOrgUnit (OrgUnitRequestDto dto) {
		OrgUnit orgUnit = modelMapper.map(dto, OrgUnit.class);
		BizPlace bizPlace = bizPlaceRepository.findByBizCode(dto.getBizCode())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. bizCode : " + dto.getBizCode()));
		
		orgUnit.setBizPlace(bizPlace);
		
		if (dto.getManagerId() != null && !dto.getManagerId().isEmpty()) {
			orgUnit.setManager(employeeMasterRepository.getReferenceById(dto.getManagerId()));
		}
		
		
		if (dto.getParentOrgUnitCode() != null && !dto.getParentOrgUnitCode().isEmpty()) {
			OrgUnit parentOrgUnit = orgUnitRepository.findByOrgCode (dto.getParentOrgUnitCode())
					.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. orgCode : " + dto.getParentOrgUnitCode()));
			
			orgUnit.setParentOrgUnit(parentOrgUnit);
		}
		
		orgUnitRepository.save(orgUnit);
		
	}

	
	// 기존 항목 수정
	@Transactional
	public void updateOrgUnit (OrgUnitRequestDto dto) {
		OrgUnit orgUnit = orgUnitRepository.findById(dto.getId())
				.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. orgCode : " + dto.getOrgCode()));
		
		orgUnit.update(dto);
	}
	
	// 코드 중복 검사
	public boolean checkOverlap (String orgCode) {
		return orgUnitRepository.existsByOrgCode (orgCode);
	}
	
	// 상세 정보 조회
	public Optional<OrgUnitDetailDto> findById (Long id) {
		return  orgUnitRepository.findById(id)
				.map(i -> modelMapper.map(i, OrgUnitDetailDto.class));
	}
	
	// 엑셀 출력용 리스트
	public List<OrgUnitExcelDto> getExcelList (List<String> bizCode, List<String> orgName, String useYn) {
		
		Specification<OrgUnit> spec = Specification.not(null);
		spec = spec.and(OrgUnitSpecs.withBizCode(bizCode))
				.and(OrgUnitSpecs.withOrgName(orgName))
				.and(OrgUnitSpecs.withUseYn(useYn));
		
		return orgUnitRepository.findAll(spec)
				.stream()
				.map(i -> modelMapper.map(i, OrgUnitExcelDto.class))
				.toList();
	}
}
