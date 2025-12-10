package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EvalResult;

@Repository
public interface EvalResultRepository extends JpaRepository<EvalResult, Long> {

	// 사번으로 page 조회
	public Page<EvalResult> findByEvalEmp_Pernr (String pernr, Pageable pageable);
	
	// 사번으로 List 조회
	public List<EvalResult> findAllByTargetEmp_PernrAndEvalStepOrderByTemplate_EndDateDesc (String pernr, String evalStep);
	
//	// 조건으로 검색
//	public Optional<EvalResult> findByEvalEmp_PernrAndYearAndSeqAndEvType (String pernr, String year, String seq, String evType);
}
