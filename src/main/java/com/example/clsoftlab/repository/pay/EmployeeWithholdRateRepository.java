package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.EmployeeWithholdRate;

@Repository
public interface EmployeeWithholdRateRepository extends JpaRepository<EmployeeWithholdRate, Long>, JpaSpecificationExecutor<EmployeeWithholdRate> {

	// 기간 중복 검사
	@Query("SELECT CASE WHEN COUNT (e) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM EmployeeWithholdRate e "
			+ "WHERE (e.employee.pernr = :empNo) "
			+ "AND (e.fromDate <= :toDate) "
			+ "AND (e.toDate >= :fromDate)")
	public boolean checkOverlap (@Param("empNo") String empNo, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
	
	// 기간 중복 검사
	@Query("SELECT CASE WHEN COUNT (e) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM EmployeeWithholdRate e "
			+ "WHERE (e.employee.pernr = :empNo) "
			+ "AND (e.id != :id) "
			+ "AND (e.fromDate <= :toDate) "
			+ "AND (e.toDate >= :fromDate)")
	public boolean checkOverlap (@Param("empNo") String empNo, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("id") Long id);
	
	// 검색용 사원 리스트 조회
	@Query("SELECT DISTINCT em "
			+ "FROM EmployeeWithholdRate e "
			+ "JOIN e.employee em "
			+ "ORDER BY em.name")
	public List<EmployeeMaster> getEmployeeList();
}
