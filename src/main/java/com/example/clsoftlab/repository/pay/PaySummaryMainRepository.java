package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.PaySummaryMain;

@Repository
public interface PaySummaryMainRepository extends JpaRepository<PaySummaryMain, Long>, JpaSpecificationExecutor<PaySummaryMain> {

	// 검색용 사번 list 조회
	@Query("SELECT DISTINCT p.employee "
			+ "FROM PaySummaryMain p "
			+ "ORDER BY p.employee.name")
	public List<EmployeeMaster> getEmployeeList ();
	
	// 중복 검사
	public boolean existsByEmployee_PernrAndPayYmAndSeqNo (String empNo, String payYm, Integer seqNo);
}
