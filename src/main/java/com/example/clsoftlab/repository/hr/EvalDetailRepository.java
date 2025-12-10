package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EvalDetail;

@Repository
public interface EvalDetailRepository extends JpaRepository<EvalDetail, Long> {
	
	// resultId로 list 조회
	public List<EvalDetail> findByEvalResult_Id(Long evaluationResultId);
	
}
