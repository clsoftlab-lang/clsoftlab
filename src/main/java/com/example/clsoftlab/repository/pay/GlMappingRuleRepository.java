package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.GlMappingRule;
import com.example.clsoftlab.entity.PayItem;

@Repository
public interface GlMappingRuleRepository extends JpaRepository<GlMappingRule, Long>, JpaSpecificationExecutor<GlMappingRule> {
	
	
	// 중복 검사
	public boolean existsByPayItem_ItemCodeAndBizCodeAndCostCntr(String itemCode, String bizCode, String costCntr);
	
	// 검색용 payItem 리스트 조회
	@Query("SELECT p "
			+ "FROM GlMappingRule g "
			+ "JOIN g.payItem p "
			+ "ORDER BY p.itemName")
	public List<PayItem> getPayItemList ();
	
	// 검색용 bizCode 리스트 조회
	@Query("SELECT DISTINCT g.bizCode "
			+ "FROM GlMappingRule g "
			+ "ORDER BY g.bizCode")
	public List<String> getBizCodeList ();
	
	// 검색용 costCntr 리스트 조회
	@Query("SELECT DISTINCT g.costCntr "
			+ "FROM GlMappingRule g "
			+ "ORDER BY g.costCntr")
	public List<String> getCostCntrList ();
}
