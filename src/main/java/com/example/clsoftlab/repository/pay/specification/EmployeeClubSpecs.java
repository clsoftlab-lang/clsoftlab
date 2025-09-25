package com.example.clsoftlab.repository.pay.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.EmployeeClub;

public class EmployeeClubSpecs {

	
	// empNo 조건
	public static Specification<EmployeeClub> withEmpNo (String empNo) {
		if(empNo == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("empNo"), "%" + empNo + "%");
	}
	
	// clubCode 조건
	public static Specification<EmployeeClub> withClubCode (String clubCode) {
		if(clubCode == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("clubItem").get("clubCode"), "%" + clubCode + "%");
	}
	
	// 기간 조건
	public static Specification<EmployeeClub> lessThanOrEqualToFromDate (LocalDate fromDate) {
		if (fromDate == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("fromDate"), fromDate);
	}
	
	public static Specification<EmployeeClub> greaterThanOrEqualToToDate (LocalDate toDate) {
		if(toDate == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("toDate"), toDate);
	}
}
