package com.example.clsoftlab.repository.pay.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.InsuranceRate;

import jakarta.persistence.criteria.Predicate;

public class InsuranceRateSpecs {

	public static Specification<InsuranceRate> withInsType (List<String> insType) {
		if (CollectionUtils.isEmpty(insType)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("insType").in(insType);
	}
	
	public static Specification<InsuranceRate> withUseYn (String useYn) {
		if (!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
	
	public static Specification<InsuranceRate> withFromDate (LocalDate fromDate) {
		if (fromDate == null) {
			return null;
		}
		
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			predicates.add(builder.greaterThanOrEqualTo(root.get("toDate"), fromDate));
			predicates.add(builder.lessThanOrEqualTo(root.get("fromDate"), fromDate));
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
