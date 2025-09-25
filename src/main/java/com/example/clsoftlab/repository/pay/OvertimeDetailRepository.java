package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.OvertimeDetail;

@Repository
public interface OvertimeDetailRepository extends JpaRepository<OvertimeDetail, Long>, JpaSpecificationExecutor<OvertimeDetail> {
	
	// 중복 검사
	public boolean existsByEmpNoAndDateAndType (String empNo, LocalDate date, String type);
	
	// 중복 검사(수정용)
	public boolean existsByEmpNoAndDateAndTypeAndIdNot (String empNo, LocalDate date, String type, Long id);
}
