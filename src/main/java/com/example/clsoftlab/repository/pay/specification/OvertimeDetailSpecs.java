package com.example.clsoftlab.repository.pay.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.OvertimeDetail;

public class OvertimeDetailSpecs {

	// empNo 조건
	public static Specification<OvertimeDetail> withEmpNo (String empNo) {
		
		if (empNo == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("empNo"), "%"+empNo+"%"); 
	}
	
	// date 조건
	public static Specification<OvertimeDetail> withDate (LocalDate date) {
		
		if (date == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("date"), date);
	}
	
	// type 조건
	public static Specification<OvertimeDetail> withType (String type) {
		if (type == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("type"), "%"+type+"%");
	}
}
