package com.example.clsoftlab.repository.pay.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.example.clsoftlab.entity.EmployeeWithholdRate;

import jakarta.persistence.criteria.Predicate;

public class EmployeeWithholdRateSpecs {

	public static Specification<EmployeeWithholdRate> withEmpNo (List<String> empNo) {
		if (CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
	
	
	public static Specification<EmployeeWithholdRate> withDateRange(LocalDate fromDate, LocalDate toDate) {

		if (fromDate == null && toDate == null) {
			return null;
		}

		return (root, query, builder) -> {
			
			List<Predicate> predicates = new ArrayList<>();

			if (toDate != null) {
				predicates.add(
						builder.lessThanOrEqualTo(root.get("fromDate"), toDate)
				);
			}

			if (fromDate != null) {
				predicates.add(
						builder.greaterThanOrEqualTo(root.get("toDate"), fromDate)
				);
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
