package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.StandardHours;

@Repository
public interface StandardHoursRepository extends JpaRepository<StandardHours, Long> {

	// 검색으로 조회
	@Query("SELECT s "
			+ "FROM StandardHours s "
			+ "WHERE (s.calYm like CONCAT('%',:calYm,'%')) "
			+ "AND (:jobGroup like '' OR s.jobGroup like CONCAT('%',:jobGroup,'%')) ")
	public Page<StandardHours> searchStandardHours (@Param(value = "calYm") String calYm, @Param(value = "jobGroup") String jobGroup, Pageable pageable);
	
	// 중복 검사
	@Query("SELECT COUNT(s) "
			+ "FROM StandardHours s "
			+ "WHERE (s.calYm = :calYm) "
			+ "AND ((s.jobGroup IS NULL AND :jobGroup IS NULL) OR s.jobGroup = :jobGroup) ")
	public long countOverlappingStandardHours(@Param(value = "calYm") String calYm, @Param(value = "jobGroup") String jobGroup);
	
}
