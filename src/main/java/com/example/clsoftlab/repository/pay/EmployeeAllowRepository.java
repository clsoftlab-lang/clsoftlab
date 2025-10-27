package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeAllow;
import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.PayItem;

@Repository
public interface EmployeeAllowRepository extends JpaRepository<EmployeeAllow, Long>, JpaSpecificationExecutor<EmployeeAllow> {

	// 사번 리스트 조회
	@Query("SELECT DISTINCT e.employeeMaster FROM EmployeeAllow e ORDER BY e.employeeMaster.name")
    public List<EmployeeMaster> findEmployees();
	
	// payItem list 조회
	@Query("SELECT DISTINCT p FROM EmployeeAllow e JOIN e.payItem p ORDER BY p.itemCode")
	public List<PayItem> findPayItem();
	
	// 기간 중복 검사
	@Query("SELECT CASE WHEN (COUNT (e) > 0 ) THEN true ELSE false END "
			+ "FROM EmployeeAllow e "
			+ "WHERE (e.employeeMaster.pernr = :empNo) "
			+ "AND (e.payItem.itemCode = :itemCode) "
			+ "AND (e.fromDate <= :toDate) "
			+ "AND (e.toDate >= :fromDate)" )
	public boolean checkOverlap (@Param("empNo") String empNo, @Param("itemCode") String itemCode, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
	
	// 기간 중복 검사 (수정시)
	@Query("SELECT CASE WHEN (COUNT (e) > 0 ) THEN true ELSE false END "
			+ "FROM EmployeeAllow e "
			+ "WHERE (e.employeeMaster.pernr = :empNo) "
			+ "AND (e.id != :id) "
			+ "AND (e.payItem.itemCode = :itemCode) "
			+ "AND (e.fromDate <= :toDate) "
			+ "AND (e.toDate >= :fromDate)" )
	public boolean checkOverlap (@Param("empNo") String empNo, @Param("itemCode") String itemCode, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("id") Long id);
}
