package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.InsuranceRate;

@Repository
public interface InsuranceRateRepository extends JpaRepository<InsuranceRate, Long>, JpaSpecificationExecutor<InsuranceRate> {
	
	// 중복 검사
	@Query("SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM InsuranceRate i "
			+ "WHERE (i.insType = :insType) "
			+ "AND (i.fromDate <= :toDate) "
			+ "AND (i.toDate >= :fromDate) ")
	public boolean checkOverlap (@Param("insType") String insType, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
	
	// 중복 검사(수정용)
	@Query("SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM InsuranceRate i "
			+ "WHERE (i.insType = :insType) "
			+ "AND (i.id != :id) "
			+ "AND (i.fromDate <= :toDate) "
			+ "AND (i.toDate >= :fromDate) ")
	public boolean checkOverlap (@Param("insType") String insType, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("id") Long id);

}
