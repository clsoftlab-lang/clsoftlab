package com.example.clsoftlab.repository.hr;

import java.util.List;
import java.util.Optional;

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
	
	// 조건으로 검색
	public Optional<EvaluationResult> findByPernrAndYearAndSeqAndEvType (String pernr, String year, String seq, String evType);
}
