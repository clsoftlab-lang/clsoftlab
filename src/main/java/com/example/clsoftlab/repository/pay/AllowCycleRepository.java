package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.AllowCycle;
import com.example.clsoftlab.entity.PayItem;

@Repository
public interface AllowCycleRepository extends JpaRepository<AllowCycle, Long>, JpaSpecificationExecutor<AllowCycle> {

	// 검색용 payItem 리스트 조회
	@Query("SELECT p "
			+ "FROM AllowCycle a "
			+ "JOIN a.payItem p "
			+ "ORDER BY p.itemName")
	public List<PayItem> getPayItemList ();
	
	// 중복 검사
	public boolean existsByPayItem_ItemCode (String itemCode);
	
}
