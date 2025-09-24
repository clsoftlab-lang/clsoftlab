package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayCertificate;

@Repository
public interface PayCertificateRepository extends JpaRepository<PayCertificate, Long> {

	// 검색어로 조회
	public Page<PayCertificate> findByYearContainingAndEmpNoContainingAndPeriodTypeContaining (String year, String empNo, String periodType, Pageable pageable);
	
	// 기간 중복 검사
	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM PayCertificate p "
			+ "WHERE (p.empNo = :empNo) "
			+ "AND (p.year = :year) "
			+ "AND (p.periodType = :periodType) "
			+ "AND (p.periodFrom <= :periodTo) "
			+ "AND (p.periodTo >= :periodFrom) ")
	public boolean checkOverlap (String empNo, String year, String periodType, LocalDate periodFrom, LocalDate periodTo);
}
