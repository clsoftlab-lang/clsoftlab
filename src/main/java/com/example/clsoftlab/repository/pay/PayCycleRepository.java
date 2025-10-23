package com.example.clsoftlab.repository.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayCycle;

@Repository
public interface PayCycleRepository extends JpaRepository<PayCycle, String>, JpaSpecificationExecutor<PayCycle> {

	
	//id 중복 검사
	public boolean existsByJobGroup (String jobGroup);
}
