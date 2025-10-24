package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.BasePay;
import com.example.clsoftlab.entity.EmployeeMaster;

@Repository
public interface BasePayRepository extends JpaRepository<BasePay, Long>, JpaSpecificationExecutor<BasePay>{

	//기간 중복 검사
	@Query("SELECT CASE WHEN (COUNT (b) > 0 ) THEN true ELSE false END "
			+ "FROM BasePay b "
			+ "WHERE (b.employeeMaster.pernr = :empNo) "
			+ "AND (b.fromDate <= :toDate) "
			+ "AND (b.toDate >= :fromDate) ")
	public boolean countOverlappingBasePay (@Param("empNo") String empNo,@Param("fromDate") LocalDate fromDate,@Param("toDate") LocalDate toDate);
	
	//기간 중복 검사 (수정시)
	@Query("SELECT CASE WHEN (COUNT (b) > 0 ) THEN true ELSE false END "
			+ "FROM BasePay b "
			+ "WHERE (b.employeeMaster.pernr = :empNo) "
			+ "AND (b.payId != :payId) "
			+ "AND (b.fromDate <= :toDate) "
			+ "AND (b.toDate >= :fromDate) ")
	public boolean countOverlappingBasePayForUpdate (@Param("empNo") String empNo,@Param("payId") long payId ,
			@Param("fromDate") LocalDate fromDate,@Param("toDate") LocalDate toDate);
	
	// 사번 리스트 조회
	@Query("SELECT DISTINCT b.employeeMaster FROM BasePay b ORDER BY b.employeeMaster.name")
    public List<EmployeeMaster> findEmployees();
}
