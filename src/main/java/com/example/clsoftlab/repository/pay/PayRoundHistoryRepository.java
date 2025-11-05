package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.PayItem;
import com.example.clsoftlab.entity.PayRoundHistory;

@Repository
public interface PayRoundHistoryRepository extends JpaRepository<PayRoundHistory, Long>, JpaSpecificationExecutor<PayRoundHistory> {
	
	// 검색용 사원 list 조회
	@Query("SELECT DISTINCT e "
			+ "FROM PayRoundHistory p "
			+ "JOIN p.employee e "
			+ "ORDER BY e.name")
	public List<EmployeeMaster> getEmployeeList ();
	
	// 검색용 payItem 조회
	@Query("SELECT DISTINCT pi "
			+ "FROM PayRoundHistory p "
			+ "JOIN p.payItem pi "
			+ "ORDER BY pi.itemCode")
	public List<PayItem> getPayItemList ();
}
