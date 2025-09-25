package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.clsoftlab.entity.EmployeeClub;

public interface EmployeeClubRepository extends JpaRepository<EmployeeClub, Long>, JpaSpecificationExecutor<EmployeeClub> {
	

	// 중복 검사
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM EmployeeClub e "
			+ "WHERE (e.empNo = :empNo) "
			+ "AND (e.clubItem.clubCode = :clubCode) "
			+ "AND (e.fromDate <= :toDate) "
			+ "AND (e.toDate >= :fromDate) ")
	public boolean checkOverlap (@Param("empNo") String empNo, @Param("clubCode") String clubCode, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
	
	// 중복 검사(수정용)
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM EmployeeClub e "
			+ "WHERE (e.empNo = :empNo) "
			+ "AND (e.id != :id) "
			+ "AND (e.clubItem.clubCode = :clubCode) "
			+ "AND (e.fromDate <= :toDate) "
			+ "AND (e.toDate >= :fromDate) ")
	public boolean checkOverlap (@Param("empNo") String empNo, @Param("clubCode") String clubCode, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("id") Long id);
}
