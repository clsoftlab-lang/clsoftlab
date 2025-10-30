package com.example.clsoftlab.repository.pay.specification;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.RetireAdjust;

public class RetireAdjustSpecs {

	public static Specification<RetireAdjust> withEmpNo (List<String> empNo) {
		if (CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("retireSummary").get("employee").get("pernr").in(empNo);
	}
	
	public static Specification<RetireAdjust> withRetireDate (LocalDate retireDate) {
		if (retireDate == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("retireDate"), retireDate);
	}
	
	public static Specification<RetireAdjust> withAdjType (String adjType) {
		if (!StringUtils.hasText(adjType)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("adjType"), adjType);
	}
}
