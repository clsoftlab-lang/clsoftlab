package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeEdu;

@Repository
public interface EmployeeEduRepository extends JpaRepository<EmployeeEdu, Long>  {

	// 사번으로 page 조회
	public Page<EmployeeEdu> findByPernr (String pernr, Pageable pageable);
	
	// 사번으로 list 조회
	public List<EmployeeEdu> findAllByPernrOrderBySeq (String pernr);
}
