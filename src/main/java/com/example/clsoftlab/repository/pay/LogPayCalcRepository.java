package com.example.clsoftlab.repository.pay;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.LogPayCalc;
import com.example.clsoftlab.entity.PayItem;

@Repository
public interface LogPayCalcRepository extends JpaRepository<LogPayCalc, Long>, JpaSpecificationExecutor<LogPayCalc> {

	// 검색용 급여월 리스트 조회
	@Query("SELECT DISTINCT l.payYm "
			+ "FROM LogPayCalc l "
			+ "ORDER BY l.payYm")
	public List<String> getPayYmList ();
	
	// 검색용 사원 리스트 조회
	@Query("SELECT DISTINCT e "
			+ "FROM LogPayCalc l "
			+ "JOIN l.employee e "
			+ "ORDER BY e.name")
	public List<EmployeeMaster> getEmployeeList ();
	
	// 검색용 payItem 리스트 조회
	@Query("SELECT DISTINCT p "
			+ "FROM LogPayCalc l "
			+ "JOIN l.payItem p "
			+ "ORDER BY p.itemName")
	public List<PayItem> getPayItemList ();
	
	// 검색용 seqNo 리스트 조회
	@Query("SELECT DISTINCT l.seqNo "
			+ "FROM LogPayCalc l "
			+ "ORDER BY l.seqNo")
	public List<Integer> getSeqNoList ();
	
	// 중복 검사
	public boolean existsByPayYmAndSeqNoAndEmployee_PernrAndPayItem_ItemCodeAndStepNo (String payYm, Integer seqNo, String empNo, String itemCode, Integer stepNo);
	
	// 특정 항목 조회
	public Optional<LogPayCalc> findByPayYmAndSeqNoAndEmployee_PernrAndPayItem_ItemCodeAndStepNo (String payYm, Integer seqNo, String empNo, String itemCode, Integer stepNo);
}
