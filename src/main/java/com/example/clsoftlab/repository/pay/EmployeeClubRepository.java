package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.clsoftlab.entity.ClubItem;
import com.example.clsoftlab.entity.EmployeeClub;
import com.example.clsoftlab.entity.EmployeeMaster;

public interface EmployeeClubRepository extends JpaRepository<EmployeeClub, Long>, JpaSpecificationExecutor<EmployeeClub> {
	

	// 중복 검사
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM EmployeeClub e "
			+ "WHERE (e.employee.pernr = :empNo) "
			+ "AND (e.clubItem.id = :clubId) "
			+ "AND (e.fromDate <= :toDate) "
			+ "AND (e.toDate >= :fromDate) ")
	public boolean checkOverlap (@Param("empNo") String empNo, @Param("clubId") Long clubId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
	
	// 중복 검사(수정용)
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END "
			+ "FROM EmployeeClub e "
			+ "WHERE (e.employee.pernr = :empNo) "
			+ "AND (e.id != :id) "
			+ "AND (e.clubItem.id = :clubId) "
			+ "AND (e.fromDate <= :toDate) "
			+ "AND (e.toDate >= :fromDate) ")
	public boolean checkOverlap (@Param("empNo") String empNo, @Param("clubId") Long clubId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate, @Param("id") Long id);
	
	// 검색용 사원 리스트 조회
	@Query("SELECT DISTINCT em "
			+ "FROM EmployeeClub e "
			+ "JOIN e.employee em "
			+ "ORDER BY em.name")
	public List<EmployeeMaster> getEmployeeList ();
	
	// 검색용 ClubItem 리스트 조회
	@Query("SELECT DISTINCT c "
			+ "FROM EmployeeClub e "
			+ "JOIN e.clubItem c "
			+ "ORDER BY c.clubName")
	public List<ClubItem> getClubItemList ();
}
