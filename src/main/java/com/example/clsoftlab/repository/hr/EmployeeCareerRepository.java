package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeCareer;

@Repository
public interface EmployeeCareerRepository extends JpaRepository<EmployeeCareer, Long> {
	
	// 사번으로 경력 조회
	public Page<EmployeeCareer> findByPernr (String pernr, Pageable pageable);
	
	// 사번으로 경력 list 조회
	public List<EmployeeCareer> findAllByPernrOrderBySeq (String pernr);
}
