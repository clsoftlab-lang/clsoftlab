package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.StandardHoursEmployee;

@Repository
public interface StandardHoursEmployeeRepository extends JpaRepository<StandardHoursEmployee, Long> {
	
	// 검색어로 조회
	@Query("SELECT s "
			+ "FROM StandardHoursEmployee s "
			+ "WHERE (s.calYm LIKE CONCAT('%',:calYm,'%')) "
			+ "AND (s.empNo LIKE CONCAT('%',:empNo,'%')) ")
	public Page<StandardHoursEmployee> searchStandardHoursEmployee (@Param("calYm") String calYm,@Param("empNo") String empNo, Pageable pageable);
	
	// 중복 검사
	public long countByCalYmAndEmpNo(String calYm, String empNo);

}
