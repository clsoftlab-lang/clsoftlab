package com.example.clsoftlab.repository.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.GlMappingRule;

@Repository
public interface GlMappingRuleRepository extends JpaRepository<GlMappingRule, Long>, JpaSpecificationExecutor<GlMappingRule> {
	
	
	// 중복 검사
	public boolean existsByItemCodeAndBizCodeAndCostCntr(String itemCode, String bizCode, String costCntr);
}
