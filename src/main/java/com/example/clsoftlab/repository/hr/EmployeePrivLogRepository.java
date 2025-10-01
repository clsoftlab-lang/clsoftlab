package com.example.clsoftlab.repository.hr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeePrivLog;

@Repository
public interface EmployeePrivLogRepository extends JpaRepository<EmployeePrivLog, Long> {

	// 로그 조회용
	Page<EmployeePrivLog> findByPernr(String pernr, Pageable pageable);

}
