package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EmployeeFamily;

@Repository
public interface EmployeeFamilyRepository extends JpaRepository<EmployeeFamily, Long> {
	
	// 검색어로 목록 조회
	public Page<EmployeeFamily> findByEmpNoContainingAndFamilyNameContainingAndFamilyTypeContaining(String empNo, String familyName, String familyType, Pageable pageable);

	// 중복 검사
	public boolean existsByEmpNoAndFamilySeq(String empNo, Integer familySeq);
}
