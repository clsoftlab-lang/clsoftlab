package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayItem;

@Repository
public interface PayItemRepository extends JpaRepository<PayItem, String>, JpaSpecificationExecutor<PayItem>  {

	// 검색용 list
	List<PayItem> findAllByOrderByItemCode();

	// 타입 조건으로 리스트로 조회
	List<PayItem> findAllByItemTypeOrderByItemCode(String itemType);

}