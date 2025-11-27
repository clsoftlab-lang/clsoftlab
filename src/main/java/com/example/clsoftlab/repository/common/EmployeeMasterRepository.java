package com.example.clsoftlab.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeMaster;

@Repository
public interface EmployeeMasterRepository extends JpaRepository<EmployeeMaster, String>, JpaSpecificationExecutor<EmployeeMaster> {

	
	// 전체 목록 조회
	public List<EmployeeMaster> findAllByOrderByName();
	
	// retire List 조회
	public List<EmployeeMaster> findByRetireDateIsNotNull();
}
