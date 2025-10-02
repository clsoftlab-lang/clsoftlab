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

import com.example.clsoftlab.dto.hr.EmployeeCertDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeCertRequestDto;
import com.example.clsoftlab.entity.EmployeeCert;
import com.example.clsoftlab.repository.hr.EmployeeCertRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeCertService {

	private final EmployeeCertRepository employeeCertRepository;
	private final ModelMapper modelMapper;
	
	public EmployeeCertService(EmployeeCertRepository employeeCertRepository, ModelMapper modelMapper) {
		this.employeeCertRepository = employeeCertRepository;
		this.modelMapper = modelMapper;
	}
	
	// 상세 정보 조회
	public Page<EmployeeCertDetailDto> searchEmployeeCert (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("seq").ascending());
		
		return employeeCertRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EmployeeCertDetailDto.class));
	}
	
	// 새 자격증 리스트 저장
	@Transactional
	public void saveEmployeeCertList (String pernr, List<EmployeeCertRequestDto> newDtoList) {
		
		Set<String> certNames = new HashSet<>();
	    Set<Integer> certSeqs = new HashSet<>();
		
		List<EmployeeCert> originalCerts = employeeCertRepository.findByPernr(pernr);
		
		// 중복 검사
		for (EmployeeCertRequestDto dto : newDtoList) {
	        if (!certNames.add(dto.getCertName())) {
	            throw new IllegalArgumentException("'" + dto.getCertName() + "' 자격증이 중복으로 입력되었습니다.");
	        }
	        if (!certSeqs.add(dto.getSeq())) {
	            throw new IllegalArgumentException("순번 '" + dto.getSeq() + "'이(가) 중복으로 입력되었습니다.");
	        }
	    }
		
		Map<Long, EmployeeCert> originalCertMap = originalCerts.stream()
	            .collect(Collectors.toMap(EmployeeCert::getId, cert -> cert));
		
		Map<Long, EmployeeCertRequestDto> dtoMap = newDtoList.stream()
	            .filter(dto -> dto.getId() != null)
	            .collect(Collectors.toMap(EmployeeCertRequestDto::getId, dto -> dto));
		
		try {

			// 삭제 대상 정리 (원본 리스트에서 없어진 것들)
			List<EmployeeCert> certsToDelete = originalCerts.stream()
					.filter(cert -> !dtoMap.containsKey(cert.getId()))
					.collect(Collectors.toList());
			
			if (!certsToDelete.isEmpty()) {
				 employeeCertRepository.deleteAllInBatch(certsToDelete);  // 순차 중복때문에 즉시 삭제.
			}
			
			for (EmployeeCertRequestDto dto : newDtoList) {
				
				// ID가 없으면 신규 등록
				if (dto.getId() == null) {
					employeeCertRepository.save(modelMapper.map(dto, EmployeeCert.class));
				} else {  // ID가 있으면 수정
					EmployeeCert employeeCert = originalCertMap.get(dto.getId());
					if (employeeCert != null) {
						employeeCert.update(dto);
						employeeCertRepository.saveAndFlush(employeeCert);
					}
				}
			}
	    } catch (DataIntegrityViolationException e) {
	        // [핵심] DB의 UNIQUE 제약조건 위반 시, 예외를 던집니다.
	        throw new IllegalArgumentException("이미 등록된 자격증이거나 순번이 중복됩니다. (DB 오류)");
	    }
		
	}
	
}
