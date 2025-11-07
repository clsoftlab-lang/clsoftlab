package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.CalcOrder;

@Repository
public interface CalcOrderRepository extends JpaRepository<CalcOrder, Long>, JpaSpecificationExecutor<CalcOrder> {
	
	
	// 코드 중복 검사
	public boolean existsByPayItem_ItemCode (String itemCode);
	
	// 계산순서 중복검사
	public boolean existsByOrderAndGroupCodeAndPayItem_ItemCodeNot (Integer order, String groupCode, String itemCode);
	
	// 검색용 CalcOrder 리스트 조회
	public List<CalcOrder> findAllByOrderByPayItem_ItemName ();
}
