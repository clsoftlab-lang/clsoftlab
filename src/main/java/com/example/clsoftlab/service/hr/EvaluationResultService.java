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

import com.example.clsoftlab.dto.hr.EvaluationDetailDto;
import com.example.clsoftlab.dto.hr.EvaluationDetailRequestDto;
import com.example.clsoftlab.dto.hr.EvaluationResultDetailDto;
import com.example.clsoftlab.dto.hr.EvaluationResultRequestDto;
import com.example.clsoftlab.entity.EvaluationDetail;
import com.example.clsoftlab.entity.EvaluationResult;
import com.example.clsoftlab.repository.hr.EvaluationDetailRepository;
import com.example.clsoftlab.repository.hr.EvaluationResultRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EvaluationResultService {

	private final EvaluationResultRepository evaluationResultRepository;
	private final EvaluationDetailRepository evaluationDetailRepository;
	private final ModelMapper modelMapper;
	
	public EvaluationResultService(EvaluationResultRepository evaluationResultRepository, EvaluationDetailRepository evaluationDetailRepository,
			ModelMapper modelMapper) {
		this.evaluationResultRepository = evaluationResultRepository;
		this.evaluationDetailRepository = evaluationDetailRepository;
		this.modelMapper = modelMapper;
	}
	
	// 사번으로 평가 결과 page 조회
	public Page<EvaluationResultDetailDto> findByPernr (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("year").descending().and(Sort.by("seq").descending()));
		
		return evaluationResultRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EvaluationResultDetailDto.class));
	}
	
	// 평가 결과 list 저장
	@Transactional
	public void saveResultList (String pernr, List<EvaluationResultRequestDto> dtoList) {
		
		// 중복 검사용 
		Set<String> checkOverlap = new HashSet<>();
		
		for (EvaluationResultRequestDto dto : dtoList) {
			String key = generatekey(dto.getYear(),dto.getSeq(),dto.getEvType());
			if (!checkOverlap.add(key)) {
				throw new IllegalArgumentException("요청 데이터에 중복된 평가 결과가 포함되어 있습니다: " + key);
			}
		}
		
		List<EvaluationResult> originalResult = evaluationResultRepository.findByPernr(pernr);
		
		Map<Long, EvaluationResult> originalMap = originalResult.stream()
				.collect(Collectors.toMap(EvaluationResult::getId, result -> result));
		
		Map<Long, EvaluationResultRequestDto> dtoMap = dtoList.stream()
				.filter(dto -> dto.getId() != null)
				.collect(Collectors.toMap(EvaluationResultRequestDto::getId, dto -> dto));
		
		try {
			// 삭제 대상
			List<EvaluationResult> resultToDelete = originalResult.stream()
					.filter(result -> !dtoMap.containsKey(result.getId()))
					.toList();
			
			if (!resultToDelete.isEmpty()) { 
				evaluationResultRepository.deleteAllInBatch(resultToDelete); 
			}
			
			for (EvaluationResultRequestDto dto : dtoList) {
				if (dto.getId() == null) { // id가 없는 경우 새로 등록
					evaluationResultRepository.save(modelMapper.map(dto, EvaluationResult.class));
				} else {
					EvaluationResult evaluationResult = originalMap.get(dto.getId());
					if (evaluationResult != null) {
						evaluationResult.update(dto);
						evaluationResultRepository.saveAndFlush(evaluationResult);
					}
				}
			}
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("순번이 중복되었습니다.");
		}
	}
	
	// 중복 검사용 key 생성
	private String generatekey(String year, String seq, String evType) {
		return year + "-" + seq + "-" + evType;
	}
	
	// 평가 detail 저장
	@Transactional
	public void saveDetailList (List<EvaluationDetailRequestDto> dtoList) {
		if (dtoList == null || dtoList.isEmpty()) {
	        return; 
	    }
		int totalScore = 0;
		
	    Long parentResultId = dtoList.get(0).getEvaluationResultId();
	    EvaluationResult parentResult = evaluationResultRepository.findById(parentResultId)
	            .orElseThrow(() -> new EntityNotFoundException("해당 평가 마스터를 찾을 수 없습니다. id: " + parentResultId));
		
		for (EvaluationDetailRequestDto dto : dtoList) {
			totalScore += dto.getPoint();
			if (dto.getId() != null) { // id가 있는 항목은 수정
				EvaluationDetail evaluationDetail = evaluationDetailRepository.findById(dto.getId())
						.orElseThrow(() -> new EntityNotFoundException("해당 항목을 찾을 수 없습니다. id : " + dto.getId()));
				evaluationDetail.update(dto);
			}  else {
				EvaluationDetail newDetail = modelMapper.map(dto, EvaluationDetail.class);
				newDetail.setEvaluationResult(parentResult);
				evaluationDetailRepository.save(newDetail);
			}
		}
	    
	    parentResult.updateTotalScoreAndGrade(totalScore);
	}
	
	// resultId로 상세 점수  조회
	public List<EvaluationDetailDto> findById (Long id) {
		return evaluationDetailRepository.findByEvaluationResultId(id).stream()
				.map(i -> modelMapper.map(i, EvaluationDetailDto.class))
				.toList();
	}
}
