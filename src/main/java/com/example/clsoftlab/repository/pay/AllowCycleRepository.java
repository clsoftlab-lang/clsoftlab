package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.AllowCycle;

@Repository
public interface AllowCycleRepository extends JpaRepository<AllowCycle, String> {

	// 검색어로 목록 조회
	public Page<AllowCycle> findByItemCodeContainingAndCycleContainingAndUseYnContaining (String itemCode, String cycle, String useYn, Pageable pageable);
	
}
