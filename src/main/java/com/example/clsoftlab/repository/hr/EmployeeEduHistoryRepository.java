package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeEduHistory;

@Repository
public interface EmployeeEduHistoryRepository extends JpaRepository<EmployeeEduHistory, Long> {

	// 사번으로 학력 page 조회
	public Page<EmployeeEduHistory> findByPernr (String pernr, Pageable pageable);
	
	// 사번으로 학력 list 조회
	public List<EmployeeEduHistory> findByPernr (String pernr);
}
