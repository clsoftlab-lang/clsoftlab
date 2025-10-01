package com.example.clsoftlab.repository.hr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeCert;

@Repository
public interface EmployeeCertRepository extends JpaRepository<EmployeeCert, Long> {
	
	// 사번으로 페이지 조회
	public Page<EmployeeCert> findByPernr (String pernr, Pageable pageable);

	// 순차 중복 검사
	public boolean existsByPernrAndSeq (String pernr, Integer seq);
	
	// 자격증명 중복 검사
	public boolean existsByPernrAndCertName (String pernr, String certName);
}
