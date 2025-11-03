package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.EmployeeStep;

@Repository
public interface EmployeeStepRepository extends JpaRepository<EmployeeStep, Long>, JpaSpecificationExecutor<EmployeeStep> {

	// 기간 검사
	@Query("SELECT CASE WHEN COUNT(e)> 0 THEN TRUE ELSE FALSE END "
			+ "FROM EmployeeStep e "
			+ "WHERE (e.employee.pernr = :pernr) "
			+ "AND (e.fromDate <= :toDate) "
			+ "AND (e.toDate >= :fromDate) ")
	
	public boolean checkOverlap (@Param("pernr") String pernr, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

	// 기간 검사 (수정용)
	@Query("SELECT CASE WHEN COUNT(e)> 0 THEN TRUE ELSE FALSE END "
			+ "FROM EmployeeStep e "
			+ "WHERE (e.employee.pernr = :pernr) "
			+ "AND (e.id != :id) "
			+ "AND (e.fromDate <= :toDate) "
			+ "AND (e.toDate >= :fromDate) ")
	
	public boolean checkOverlap (@Param("pernr") String pernr, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("id") Long id);
	
	// 검색용 사번 리스트 조회
	@Query("SELECT DISTINCT em "
			+ "FROM EmployeeStep e "
			+ "JOIN e.employee em "
			+ "ORDER BY em.name")
	public List<EmployeeMaster> getEmployeeList ();
	
	// 검색용 직군 리스트 조회
	@Query("SELECT DISTINCT e.gradeCode "
			+ "FROM EmployeeStep e "
			+ "ORDER BY e.gradeCode ")
	public List<String> getGradeCodeList ();
}
