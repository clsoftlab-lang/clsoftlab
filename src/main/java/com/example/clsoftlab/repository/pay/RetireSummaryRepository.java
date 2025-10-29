package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.RetireSummary;

@Repository
public interface RetireSummaryRepository extends JpaRepository<RetireSummary, Long>, JpaSpecificationExecutor<RetireSummary> {

	// 중복 검사
	public boolean existsByEmployee_Pernr (String pernr);
	
	// 사번 리스트 조회
	@Query ("SELECT DISTINCT r.employee "
			+ "FROM RetireSummary r "
			+ "ORDER BY r.employee.name ")
	public List<EmployeeMaster> getEmployeeList ();
}
