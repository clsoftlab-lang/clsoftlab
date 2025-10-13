package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EvaluationDetail;

@Repository
public interface EvaluationDetailRepository extends JpaRepository<EvaluationDetail, Long> {
	
	// 사번, 년도, 순차, 유형으로 page조회
	public Page<EvaluationDetail> findByPernrAndYearAndSeqAndEvType(String pernr, String year, String seq, String evType, Pageable pageable);
	
	
	// 사번, 년도, 순차, 유형으로 list 조회
	public List<EvaluationDetail> findByPernrAndYearAndSeqAndEvType (String pernr, String year, String seq, String evType);
}
