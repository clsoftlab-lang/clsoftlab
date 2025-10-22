package com.example.clsoftlab.repository.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayDetail;

@Repository
public interface PayDetailRepository extends JpaRepository<PayDetail, Long> , JpaSpecificationExecutor<PayDetail> {

	// 중복 검사
	public boolean existsByPayYmAndEmployeeMaster_PernrAndPayItem_ItemCodeAndSeqNo (String payYm, String empNo, String itemCode, Integer seqNo);
	
}
