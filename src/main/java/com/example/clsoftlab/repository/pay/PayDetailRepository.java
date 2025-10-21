package com.example.clsoftlab.repository.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayDetail;

@Repository
public interface PayDetailRepository extends JpaRepository<PayDetail, Long> , JpaSpecificationExecutor<PayDetail> {

	
}
