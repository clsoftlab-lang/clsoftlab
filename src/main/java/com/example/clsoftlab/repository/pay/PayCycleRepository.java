package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayCycle;

@Repository
public interface PayCycleRepository extends JpaRepository<PayCycle, String> {

	// 검색어로 조회
	@Query("SELECT p "
			+ "FROM PayCycle p "
			+ "WHERE (p.jobGroup like CONCAT('%',:jobGroup,'%')) "
			+ "AND (p.useYn like CONCAT('%',:useYn,'%')) ")
	public Page<PayCycle> searchPayCycle (@Param("jobGroup") String jobGroup, @Param("useYn") String useYn, Pageable pageable);
	
	
	//id 중복 검사
	@Query("SELECT COUNT(p) "
			+ "FROM PayCycle p "
			+ "WHERE p.jobGroup like :jobGroup ")
	public long countOverlappingPayCycle(@Param("jobGroup") String jobGroup);
}
