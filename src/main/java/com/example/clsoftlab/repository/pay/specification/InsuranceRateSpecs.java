package com.example.clsoftlab.repository.pay.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.InsuranceRate;

public interface InsuranceRateSpecs {

	public static Specification<InsuranceRate> withInsType (String insType) {
		if (insType == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("insType"), insType);
	}
	
	public static Specification<InsuranceRate> lessThanOrEqualToFromDate (LocalDate fromDate) {
		if (fromDate == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("fromDate"), fromDate);
	}
	
	public static Specification<InsuranceRate> greaterThanOrEqualToFromDate (LocalDate fromDate) {
		if (fromDate == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("toDate"), fromDate);
	}
	
	public static Specification<InsuranceRate> withUseYn (String useYn) {
		if (useYn == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("useYn"), useYn);
	}
}
