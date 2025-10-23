package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayRule;

@Repository
public interface PayRuleRepository extends JpaRepository<PayRule, Long>, JpaSpecificationExecutor<PayRule> {

	
	// 계산 규칙 목록 검색
	@Query("SELECT p "
			+ "FROM PayRule p "
			+ "WHERE (itemCode like concat('%',:itemCode,'%')) "
			+ "AND (ruleType like concat('%', :ruleType, '%')) "
			+ "AND (useYn like concat('%', :useYn, '%')) ")
	public Page<PayRule> search( @Param("itemCode") String itemCode,
            @Param("ruleType") String ruleType,
            @Param("useYn") String useYn,
            Pageable pageable);
	
	// 해당 아이템 코드와 기간을 받아서 중복이 있는지 확인
	@Query("SELECT COUNT(p) "
			+ "FROM PayRule p "
			+ "WHERE (p.itemCode = :itemCode) "
			+ "AND (p.fromDate <= :toDate) "
			+ "AND (p.toDate >= :fromDate) ") 
	public long countOverlappingRules (@Param("itemCode") String itemCode,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);
	
	// 해당 아이템 코드와 기간을 받아서 중복이 있는지 확인 (자기 자신은 제외.)
		@Query("SELECT COUNT(p) "
				+ "FROM PayRule p "
				+ "WHERE (p.itemCode = :itemCode) "
				+ "AND (p.ruleId != :ruleId) "
				+ "AND (p.fromDate <= :toDate) "
				+ "AND (p.toDate >= :fromDate) ") 
		public long countOverlappingRulesForUpdate (@Param("itemCode") String itemCode,
				@Param("ruleId") long ruleId,
	            @Param("fromDate") LocalDate fromDate,
	            @Param("toDate") LocalDate toDate);
}
