package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeReward;

@Repository
public interface EmployeeRewardRepository extends JpaRepository<EmployeeReward, Long> {

	// 사번으로 포상 page 조회
	public Page<EmployeeReward> findByPernr (String pernr, Pageable pageable);
	
	// 사번으로 포상 list 조회
	public List<EmployeeReward> findByPernr (String pernr);
}
