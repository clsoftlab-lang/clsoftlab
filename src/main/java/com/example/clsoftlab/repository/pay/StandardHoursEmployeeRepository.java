package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;
import com.example.clsoftlab.entity.StandardHoursEmployee;

@Repository
public interface StandardHoursEmployeeRepository extends JpaRepository<StandardHoursEmployee, Long>, JpaSpecificationExecutor<StandardHoursEmployee> {
	
	// 중복 검사
	public boolean existsByCalYmAndEmployee_Pernr(String calYm, String empNo);
	
	// 사번 list 조회
	@Query("SELECT DISTINCT s.employee "
			+ "FROM StandardHoursEmployee s "
			+ "ORDER BY s.employee.name")
	public List<EmployeeMaster> getEmployeeList ();
}
