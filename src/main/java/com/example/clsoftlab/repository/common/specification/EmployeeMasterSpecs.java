package com.example.clsoftlab.repository.common.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.example.clsoftlab.entity.EmployeeMaster;

public class EmployeeMasterSpecs {

	public static Specification<EmployeeMaster> withPernr (List<String> pernr) {
		if (CollectionUtils.isEmpty(pernr)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("pernr").in(pernr);
	}
	
	public static Specification<EmployeeMaster> withDutyCode (List<String> dutyCode) {
		if (CollectionUtils.isEmpty(dutyCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("dutyCode").in(dutyCode);
	}
	
	public static Specification<EmployeeMaster> withRankCode (List<String> rankCode) {
		if (CollectionUtils.isEmpty(rankCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("rankCode").in(rankCode);
	}
	
	public static Specification<EmployeeMaster> withEmpStatus (List<String> empStatus) {
		if (CollectionUtils.isEmpty(empStatus)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("empStatus").in(empStatus);
	}
}
