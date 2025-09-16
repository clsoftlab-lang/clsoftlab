package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.BackpayRule;

@Repository
public interface BackpayRuleRepository extends JpaRepository<BackpayRule, Long> {

	// 검색어로 조회
	@Query("SELECT b "
			+ "FROM BackpayRule b "
			+ "WHERE (b.appliedItem.itemCode like CONCAT('%',:appliedItemCode,'%') ) "
			+ "AND (b.baseItem.itemCode like CONCAT('%',:baseItemCode,'%'))"  )
	public Page<BackpayRule> searchBackpayRule (@Param("appliedItemCode") String appliedItemCode, @Param("baseItemCode") String baseItemCode, Pageable pageable);
	
	// 해당 아이템 코드를 받아서 중복이 있는지 확인
	@Query("SELECT COUNT(b) "
			+ "FROM BackpayRule b "
			+ "WHERE (b.appliedItem.itemCode like :appliedItemCode) "
			+ "AND (b.baseItem.itemCode like :baseItemCode) ") 
	public long countOverlappingRules (@Param("appliedItemCode") String appliedItemCode,
            @Param("baseItemCode") String baseItemCode);
}
