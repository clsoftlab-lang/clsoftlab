package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EvaluationDetail;

@Repository
public interface EvaluationDetailRepository extends JpaRepository<EvaluationDetail, Long> {
	
	// resultId로 list 조회
	public List<EvaluationDetail> findByEvaluationResultId(Long evaluationResultId);
	
}
