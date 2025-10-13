package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EvaluationResult;

@Repository
public interface EvaluationResultRepository extends JpaRepository<EvaluationResult, Long> {

	// 사번으로 page 조회
	public Page<EvaluationResult> findByPernr (String pernr, Pageable pageable);
	
	// 사번으로 List 조회
	public List<EvaluationResult> findByPernr (String pernr);
}
