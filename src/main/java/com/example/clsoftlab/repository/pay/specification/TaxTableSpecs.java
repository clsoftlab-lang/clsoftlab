package com.example.clsoftlab.repository.pay.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.TaxTable;

public class TaxTableSpecs {

	 // 연도(year) 조건
    public static Specification<TaxTable> withYear(Integer year) {
    	if(year == null) {
    		return null;
    	}
        return (root, query, builder) -> builder.equal(root.get("id").get("year"), year);
    }

    // 가족 수(familyCount) 조건
    public static Specification<TaxTable> withFamilyCount(Integer familyCount) {
    	if(familyCount == null) {
    		return null;
    	}
        return (root, query, builder) -> builder.equal(root.get("id").get("familyCount"), familyCount);
    }

    // 소득 구간(incomeAmount) 조건
    public static Specification<TaxTable> greaterThanOrEqualToIncome(Long from) {
    	if (from == null) {
    		return null;
    	}
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("id").get("incomeAmount"), from);
    }
    public static Specification<TaxTable> lessThanOrEqualToIncome(Long to) {
    	if (to == null) {
    		return null;
    	}
    	return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("id").get("incomeAmount"), to);
    }
}
