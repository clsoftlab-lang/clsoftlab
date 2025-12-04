package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeLang;

@Repository
public interface EmployeeLangRepository extends JpaRepository<EmployeeLang, Long> {

	// 사번으로 어학 조회
	public Page<EmployeeLang> findByPernr (String pernr, Pageable pageable);
	
	// 사번으로 어학 리스트 조회
	public List<EmployeeLang> findAllByPernrOrderBySeq (String pernr);
}
