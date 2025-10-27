package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.dto.pay.PayCycleListDto;
import com.example.clsoftlab.entity.PayCycle;

@Repository
public interface PayCycleRepository extends JpaRepository<PayCycle, String>, JpaSpecificationExecutor<PayCycle> {

	
	// 검색창 list 조회
	@Query ("SELECT new com.example.clsoftlab.dto.pay.PayCycleListDto(p.jobGroup) "
			+ "FROM PayCycle p ")	
	public List<PayCycleListDto> getPayCycleList ();
	
}
