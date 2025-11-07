package com.example.clsoftlab.repository.pay.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.SimplifiedTaxTable;

import jakarta.persistence.criteria.Predicate;

public class SimplifiedTaxTableSpecs {

	public static Specification<SimplifiedTaxTable> wihtYear (String year) {
		if (!StringUtils.hasText(year)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("year"), year);
	}
	
	public static Specification<SimplifiedTaxTable> withFamilyCount (Integer familyCount) {
		if (familyCount == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("familyCount"), familyCount);
	}
	
	public static Specification<SimplifiedTaxTable> withIncomeAmtRange (Long incomeAmtFrom, Long incomeAmtTo) {
		if (incomeAmtFrom == null && incomeAmtTo == null) {
			return null;
		}
		
		return (root, query, builder) -> {
			
			List<Predicate> predicates = new ArrayList<>();
			
			if (incomeAmtFrom != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("incomeAmt"), incomeAmtFrom));
			}
			
			if (incomeAmtTo != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("incomeAmt"), incomeAmtTo));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
