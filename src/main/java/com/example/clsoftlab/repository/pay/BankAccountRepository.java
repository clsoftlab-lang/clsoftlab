package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

	// 검색어로 조회
	public Page<BankAccount> findByEmpNoContainingAndAccountTypeContainingAndUseYnContaining (String empNo, String accountType, String useYn, Pageable pageable);
	
	// 중복 검사
	@Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM BankAccount b "
			+ "WHERE (b.empNo = :empNo) "
			+ "AND (b.accountType = :accountType) "
			+ "AND (b.accountNoHash = :accountNo) "
			+ "AND (b.fromDate <= :toDate) "
			+ "AND (b.toDate >= :fromDate) ")
	public boolean checkOverlap(@Param("empNo") String empNo, @Param("accountType") String accountType, @Param("accountNo") String accountNo, 
			@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
	
	// 중복 검사(수정용)
	@Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM BankAccount b "
			+ "WHERE (b.empNo = :empNo) "
			+ "AND (b.id != :id) "
			+ "AND (b.accountType = :accountType) "
			+ "AND (b.accountNoHash = :accountNo) "
			+ "AND (b.fromDate <= :toDate) "
			+ "AND (b.toDate >= :fromDate) ")
	public boolean checkOverlap(@Param("empNo") String empNo, @Param("accountType") String accountType, @Param("accountNo") String accountNo, 
			@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("id") long id);
	
}
