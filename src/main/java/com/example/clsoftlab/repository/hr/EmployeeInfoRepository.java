package com.example.clsoftlab.repository.hr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeInfo;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, String> {
	
}
