package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.PayDeductReason;
import com.example.clsoftlab.entity.PayItem;

@Repository
public interface PayDeductReasonRepository extends JpaRepository<PayDeductReason, Long>,JpaSpecificationExecutor<PayDeductReason> {

	
	// 중복 검사
	public boolean existsByEmployee_PernrAndPayYmAndSeqNoAndPayItem_ItemCode (String empNo, String PayYm, int seqNo, String itemCode);
	
	
	// 검색용 사번 조회
	@Query("SELECT DISTINCT e "
			+ "FROM PayDeductReason p "
			+ "JOIN p.employee e "
			+ "ORDER BY e.name ")
	public List<EmployeeMaster> getEmployeeList ();
	
	// 검색용 payItem 조회
	@Query("SELECT DISTINCT pi "
			+ "FROM PayDeductReason p "
			+ "JOIN p.payItem pi "
			+ "ORDER BY pi.itemCode ")
	public List<PayItem> getPayItemList ();
}
