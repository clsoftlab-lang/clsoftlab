package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayDeductReason;

@Repository
public interface PayDeductReasonRepository extends JpaRepository<PayDeductReason, Long> {

	// 검색어로 전체목록 조회
	public Page<PayDeductReason> findByEmpNoContainingAndPayYmContainingAndItemCodeContaining (@Param("empNo") String empNo, @Param("payYm") String payYm, @Param("itemCode") String itemCode, Pageable pageable);
	
	// 중복 검사
	public boolean existsByEmpNoAndPayYmAndSeqNoAndItemCode (String empNo, String PayYm, int seqNo, String itemCode);
	
	// 중복 검사(수정용)
	public boolean existsByEmpNoAndPayYmAndSeqNoAndItemCodeAndIdNot (String empNo, String PayYm, int seqNo, String itemCode, long id);
}
