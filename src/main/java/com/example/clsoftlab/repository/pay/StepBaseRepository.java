package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.StepBase;

@Repository
public interface StepBaseRepository extends JpaRepository<StepBase, Long>, JpaSpecificationExecutor<StepBase>{
	
	// 중복 검사
	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM StepBase s "
			+ "WHERE (s.gradeCode = :gradeCode) "
			+ "AND (s.stepNo = :stepNo) "
			+ "AND (s.fromDate <= :toDate) "
			+ "AND (s.toDate >= :fromDate)")
	public boolean existsOverlap (@Param("gradeCode") String gradeCode,@Param("stepNo") String stepNo,@Param("fromDate") LocalDate fromDate,@Param("toDate") LocalDate toDate);
	
	// 중복 검사(수정용)
	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM StepBase s "
			+ "WHERE (s.gradeCode = :gradeCode) "
			+ "AND (s.id != :id) "
			+ "AND (s.stepNo = :stepNo) "
			+ "AND (s.fromDate <= :toDate) "
			+ "AND (s.toDate >= :fromDate)")
	public boolean existsOverlap (@Param("gradeCode") String gradeCode,@Param("stepNo") String stepNo,@Param("fromDate") LocalDate fromDate,@Param("toDate") LocalDate toDate,@Param("id") long id);

	// 검색용 직군 코드 조회
	@Query("SELECT DISTINCT s.gradeCode "
			+ "FROM StepBase s "
			+ "ORDER BY s.gradeCode")
	public List<String> getGadeCodeList();
}
