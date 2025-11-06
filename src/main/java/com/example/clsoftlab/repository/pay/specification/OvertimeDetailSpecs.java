package com.example.clsoftlab.repository.pay.specification;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.OvertimeDetail;

public class OvertimeDetailSpecs {

	// empNo 조건
	public static Specification<OvertimeDetail> withEmpNo (List<String> empNo) {
		
		if (CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo); 
	}
	
	// date 조건
	public static Specification<OvertimeDetail> withDate (LocalDate date) {
		
		if (date == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("date"), date);
	}
	
	// type 조건
	public static Specification<OvertimeDetail> withType (List<String> type) {
		if (CollectionUtils.isEmpty(type)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("type").in(type);
	}
}
