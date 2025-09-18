package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.BasePay;

@Repository
public interface BasePayRepository extends JpaRepository<BasePay, Long>{

	// 검색어로 전체 목록 조회
	@Query("SELECT b "
			+ "FROM BasePay b "
			+ "WHERE (b.empNo LIKE CONCAT('%',:empNo,'%')) "
			+ "AND (b.baseUnit LIKE CONCAT('%',:baseUnit,'%')) ")
	public Page<BasePay> searchBasePay(@Param("empNo") String empNo,@Param("baseUnit") String baseUnit, Pageable pageable);
	
	//기간 중복 검사
	@Query("SELECT COUNT(b) "
			+ "FROM BasePay b "
			+ "WHERE (b.empNo LIKE CONCAT('%',:empNo,'%')) "
			+ "AND (b.fromDate <= :toDate) "
			+ "AND (b.toDate >= :fromDate) ")
	public long countOverlappingBasePay (@Param("empNo") String empNo,@Param("fromDate") LocalDate fromDate,@Param("toDate") LocalDate toDate);
	
	//기간 중복 검사 (수정시)
	@Query("SELECT COUNT(b) "
			+ "FROM BasePay b "
			+ "WHERE (b.empNo LIKE CONCAT('%',:empNo,'%')) "
			+ "AND (b.payId != :payId) "
			+ "AND (b.fromDate <= :toDate) "
			+ "AND (b.toDate >= :fromDate) ")
	public long countOverlappingBasePayForUpdate (@Param("empNo") String empNo,@Param("payId") long payId ,
			@Param("fromDate") LocalDate fromDate,@Param("toDate") LocalDate toDate);
}
