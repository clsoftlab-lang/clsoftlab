package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeCert;

@Repository
public interface EmployeeCertRepository extends JpaRepository<EmployeeCert, Long> {
	
	// 사번으로 페이지 조회
	public Page<EmployeeCert> findByPernr (String pernr, Pageable pageable);

	// 사번으로 list조회
	public List<EmployeeCert> findAllByPernrOrderBySeq(String pernr);
}
