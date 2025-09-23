package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayRoundHistory;

@Repository
public interface PayRoundHistoryRepository extends JpaRepository<PayRoundHistory, Long> {

	// 검색어로 목록 조회
	Page<PayRoundHistory> findByEmpNoContainingAndPayYmContainingAndItemCodeContaining (String empNo, String payYm, String itemCode, Pageable pageable);
}
