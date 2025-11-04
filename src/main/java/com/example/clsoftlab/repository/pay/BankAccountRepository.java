package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.BankAccount;
import com.example.clsoftlab.entity.EmployeeMaster;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>, JpaSpecificationExecutor<BankAccount> {

	// 중복 검사
	@Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM BankAccount b "
			+ "WHERE (b.employee.pernr = :empNo) "
			+ "AND (b.accountType = :accountType) "
			+ "AND (b.accountNoHash = :accountNo) "
			+ "AND (b.fromDate <= :toDate) "
			+ "AND (b.toDate >= :fromDate) ")
	public boolean checkOverlap(@Param("empNo") String empNo, @Param("accountType") String accountType, @Param("accountNo") String accountNo, 
			@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
	
	// 중복 검사(수정용)
	@Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM BankAccount b "
			+ "WHERE (b.employee.pernr = :empNo) "
			+ "AND (b.id != :id) "
			+ "AND (b.accountType = :accountType) "
			+ "AND (b.accountNoHash = :accountNo) "
			+ "AND (b.fromDate <= :toDate) "
			+ "AND (b.toDate >= :fromDate) ")
	public boolean checkOverlap(@Param("empNo") String empNo, @Param("accountType") String accountType, @Param("accountNo") String accountNo, 
			@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("id") long id);
	
	// 검색용 사번 리스트 조회
	@Query("SELECT e "
			+ "FROM BankAccount b "
			+ "JOIN b.employee e "
			+ "ORDER BY e.name ")
	public List<EmployeeMaster> getEmployeeList ();
}
