package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.CalcOrder;

@Repository
public interface CalcOrderRepository extends JpaRepository<CalcOrder, String> {
	
	// 검색어로 전체 목록 조회
	public Page<CalcOrder> findByItemCodeContainingAndGroupCodeContainingAndUseYnContaining(String itemCode, String groupCode, String useYn, Pageable pageable);
	
	// 코드 중복 검사
	public boolean existsByItemCode (String itemCode);
	
	// 계산순서 중복검사
	public boolean existsByOrderAndGroupCode (Integer order, String groupCode);
}
