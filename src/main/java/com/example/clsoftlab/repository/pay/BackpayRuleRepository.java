package com.example.clsoftlab.repository.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.BackpayRule;

@Repository
public interface BackpayRuleRepository extends JpaRepository<BackpayRule, Long>, JpaSpecificationExecutor<BackpayRule> {

	
	// 해당 아이템 코드를 받아서 중복이 있는지 확인
	public boolean existsByAppliedItem_ItemCodeAndBaseItem_ItemCode( String appliedItemCode, String baseItemCode);
}
