package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.CalcOrder;

@Repository
public interface CalcOrderRepository extends JpaRepository<CalcOrder, String> {
	
	// 검색어로 전체 목록 조회
	 @Query(value = "SELECT c "
	 		+ "FROM CalcOrder c " +
             "JOIN FETCH c.payItem pi " +
             "LEFT JOIN FETCH c.dependsOn do " +
             "LEFT JOIN FETCH do.payItem  " +
             "WHERE (:itemCode IS NULL OR :itemCode = '' OR c.itemCode LIKE %:itemCode%) " +
             "AND (:groupCode IS NULL OR :groupCode = '' OR c.groupCode = :groupCode) " +
             "AND (:useYn IS NULL OR :useYn = '' OR c.useYn = :useYn)",
     countQuery = "SELECT COUNT(c) "
     		+ "FROM CalcOrder c " + // Page 객체를 위한 카운트 쿼리
                  "WHERE (:itemCode IS NULL OR :itemCode = '' OR c.itemCode LIKE %:itemCode%) " +
                  "AND (:groupCode IS NULL OR :groupCode = '' OR c.groupCode = :groupCode) " +
                  "AND (:useYn IS NULL OR :useYn = '' OR c.useYn = :useYn)")
	public Page<CalcOrder> searchCalcOrder(String itemCode, String groupCode, String useYn, Pageable pageable);
	
	// 코드 중복 검사
	public boolean existsByItemCode (String itemCode);
	
	// 계산순서 중복검사
	public boolean existsByOrderAndGroupCodeAndItemCodeNot (Integer order, String groupCode, String itemCode);
}
