package com.example.clsoftlab.repository.pay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.OvertimeDetail;

@Repository
public interface OvertimeDetailRepository extends JpaRepository<OvertimeDetail, Long>, JpaSpecificationExecutor<OvertimeDetail> {
	
	// 중복 검사
	public boolean existsByEmployee_PernrAndDateAndType (String empNo, LocalDate date, String type);
	
	// 중복 검사(수정용)
	public boolean existsByEmployee_PernrAndDateAndTypeAndIdNot (String empNo, LocalDate date, String type, Long id);
	
	// 검색용 사원 리스트 조회
	@Query("SELECT DISTINCT e "
			+ "FROM OvertimeDetail o "
			+ "JOIN o.employee e "
			+ "ORDER BY e.name")
	public List<EmployeeMaster> getEmployeeList ();
}
