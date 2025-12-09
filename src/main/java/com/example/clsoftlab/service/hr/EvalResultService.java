package com.example.clsoftlab.service.hr;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.hr.EvalResultDetailDto;
import com.example.clsoftlab.dto.hr.EvaluationDetailDto;
import com.example.clsoftlab.dto.hr.EvaluationDetailRequestDto;
import com.example.clsoftlab.entity.EvalResult;
import com.example.clsoftlab.entity.EvaluationDetail;
import com.example.clsoftlab.repository.hr.EvalResultRepository;
import com.example.clsoftlab.repository.hr.EvaluationDetailRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvalResultService {

	private final EvalResultRepository evaluationResultRepository;
	private final EvaluationDetailRepository evaluationDetailRepository;
	private final ModelMapper modelMapper;
	
	
	// 사번으로 평가 결과 page 조회
	public Page<EvalResultDetailDto> findByPernr (String pernr, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("year").descending().and(Sort.by("seq").descending()));
		
		return evaluationResultRepository.findByPernr(pernr, pageable)
				.map(i -> modelMapper.map(i, EvalResultDetailDto.class));
	}
	
//	// 평가 결과 list 저장
//	@Transactional
//	public void saveResultList (String pernr, List<EvalResultRequestDto> dtoList) {
//		
//		// 중복 검사용 
//		Set<String> checkOverlap = new HashSet<>();
//		
//		for (EvalResultRequestDto dto : dtoList) {
//			String key = generatekey(dto.getYear(),dto.getEvType());
//			if (!checkOverlap.add(key)) {
//				throw new IllegalArgumentException("요청 데이터에 중복된 평가 결과가 포함되어 있습니다: " + key);
//			}
//		}
//		
//		List<EvalResult> originalResult = evaluationResultRepository.findByPernr(pernr);
//		
//		Map<Long, EvalResult> originalMap = originalResult.stream()
//				.collect(Collectors.toMap(EvalResult::getId, result -> result));
//		
//		Map<Long, EvalResultRequestDto> dtoMap = dtoList.stream()
//				.filter(dto -> dto.getId() != null)
//				.collect(Collectors.toMap(EvalResultRequestDto::getId, dto -> dto));
//		
//		try {
//			// 삭제 대상
//			List<EvalResult> resultToDelete = originalResult.stream()
//					.filter(result -> !dtoMap.containsKey(result.getId()))
//					.toList();
//			
//			if (!resultToDelete.isEmpty()) { 
//				evaluationResultRepository.deleteAllInBatch(resultToDelete); 
//			}
//			
//			for (EvalResultRequestDto dto : dtoList) {
//				if (dto.getId() == null) { // id가 없는 경우 새로 등록
//					evaluationResultRepository.save(modelMapper.map(dto, EvalResult.class));
//				} else {
//					EvalResult evaluationResult = originalMap.get(dto.getId());
//					if (evaluationResult != null) {
//						evaluationResult.update(dto);
//						evaluationResultRepository.saveAndFlush(evaluationResult);
//					}
//				}
//			}
//		} catch (DataIntegrityViolationException e) {
//			throw new IllegalArgumentException("순번이 중복되었습니다.");
//		}
//	}
	
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
	    EvalResult parentResult = evaluationResultRepository.findById(parentResultId)
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
