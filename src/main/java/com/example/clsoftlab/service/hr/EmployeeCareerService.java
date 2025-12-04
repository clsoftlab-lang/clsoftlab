package com.example.clsoftlab.service.hr;

import java.time.Period;
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

import com.example.clsoftlab.dto.hr.EmployeeCareerDetailDto;
import com.example.clsoftlab.dto.hr.EmployeeCareerRequestDto;
import com.example.clsoftlab.dto.hr.EmployeeCareerSimpleDto;
import com.example.clsoftlab.entity.EmployeeCareer;
import com.example.clsoftlab.repository.hr.EmployeeCareerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeCareerService {

	private final EmployeeCareerRepository employeeCareerRepository;
	private final ModelMapper modelMapper;
	
	// 사번으로 경력 검색
	public Page<EmployeeCareerDetailDto> findByPernr (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("seq").ascending());
		return employeeCareerRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EmployeeCareerDetailDto.class));
	}
	
	// 사번으로 simpleList 검색
	public List<EmployeeCareerSimpleDto> getSimpleList (String pernr) {
		List<EmployeeCareer> careers = employeeCareerRepository.findAllByPernrOrderBySeq(pernr);

        return careers.stream()
                .map(entity -> {
                    // 1. ModelMapper로 기본 필드 자동 매핑
                    EmployeeCareerSimpleDto dto = modelMapper.map(entity, EmployeeCareerSimpleDto.class);

                    // 2. 기간 계산 및 문자열 포맷팅
                    String periodStr = "-";
                    if (entity.getStartDate() != null && entity.getEndDate() != null) {
                        Period period = Period.between(entity.getStartDate(), entity.getEndDate());

                        StringBuilder sb = new StringBuilder();
                        if (period.getYears() > 0) sb.append(period.getYears()).append("년 ");
                        if (period.getMonths() > 0) sb.append(period.getMonths()).append("개월");

                        periodStr = sb.length() > 0 ? sb.toString() : "1개월 미만";
                    }

                    // 3. Setter로 계산된 값 주입
                    dto.setTotalPeriod(periodStr);

                    return dto;
                })
                .toList();
	}
	
	// 경력 리스트 저장
	@Transactional
	public void saveEmployeeCareerList (String pernr, List<EmployeeCareerRequestDto> dtoList) {
		
		Set<Integer> careerSeqs = new HashSet<>();
		
		List<EmployeeCareer> originalCareer = employeeCareerRepository.findAllByPernrOrderBySeq(pernr);
		
		// 순차 검사
		for (EmployeeCareerRequestDto dto : dtoList) {
			if (!careerSeqs.add(dto.getSeq())) {
				throw new IllegalArgumentException("순번 '" + dto.getSeq() + "'이(가) 중복으로 입력되었습니다.");
			}
		}
		
		Map<Long, EmployeeCareer> originalMap = originalCareer.stream()
				.collect(Collectors.toMap(EmployeeCareer::getId, career -> career));
		
		Map<Long, EmployeeCareerRequestDto> dtoMap = dtoList.stream()
				.filter(dto -> dto.getId() != null)
				.collect(Collectors.toMap(EmployeeCareerRequestDto::getId, dto -> dto));
		
		try {
			// 삭제 대상 정리
			List<EmployeeCareer> careerToDelete = originalCareer.stream()
					.filter(career -> !dtoMap.containsKey(career.getId()))
					.toList();
			
			if (!careerToDelete.isEmpty()) {
				employeeCareerRepository.deleteAllInBatch(careerToDelete);
			}
			
			for (EmployeeCareerRequestDto dto : dtoList) {
				
				// Id가 없으면 신규 등록
				if (dto.getId() == null) {
					employeeCareerRepository.save(modelMapper.map(dto, EmployeeCareer.class));
				} else { // Id가 있으면 수정
					EmployeeCareer employeeCareer = originalMap.get(dto.getId());
					if (employeeCareer != null) {
						employeeCareer.update(dto);
						employeeCareerRepository.saveAndFlush(employeeCareer);
					}
					
				}
			}
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("순번이 중복되었습니다.");
		}
	}
}
