package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.PayCertificate;

@Repository
public interface PayCertificateRepository extends JpaRepository<PayCertificate, Long>, JpaSpecificationExecutor<PayCertificate> {

	// 기간 중복 검사
	@Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM PayCertificate p "
			+ "WHERE (p.employee.pernr = :empNo) "
			+ "AND (p.year = :year) "
			+ "AND (p.periodType = :periodType) "
			+ "AND (p.periodFrom <= :periodTo) "
			+ "AND (p.periodTo >= :periodFrom) ")
	public boolean checkOverlap (String empNo, String year, String periodType, LocalDate periodFrom, LocalDate periodTo);
	
	// 검색용 사원 list 조회
	@Query ("SELECT DISTINCT e "
			+ "FROM PayCertificate p "
			+ "JOIN p.employee e "
			+ "ORDER BY e.name")
	public List<EmployeeMaster> getEmployeeList ();

	// cert_no 숫자 count
	public Integer countByYearAndEmployee_Pernr(String year, String empNo);
}
