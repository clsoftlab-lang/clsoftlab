package com.example.clsoftlab.repository.pay.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.example.clsoftlab.entity.EmployeeStep;

import jakarta.persistence.criteria.Predicate;

public class EmployeeStepSpecs {

	public static Specification<EmployeeStep> withPernr (List<String> pernr) {
		if (CollectionUtils.isEmpty(pernr)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(pernr);
	}
	
	public static Specification<EmployeeStep> withGradeCode (List<String> gradeCode) {
		if (CollectionUtils.isEmpty(gradeCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("gradeCode").in(gradeCode);
	}
	
	public static Specification<EmployeeStep> withDateRange(LocalDate searchFrom, LocalDate searchTo) {

		if (searchFrom == null && searchTo == null) {
			return null;
		}

		return (root, query, builder) -> {
			
			List<Predicate> predicates = new ArrayList<>();

			if (searchTo != null) {
				predicates.add(
						builder.lessThanOrEqualTo(root.get("fromDate"), searchTo)
				);
			}

			if (searchFrom != null) {
				predicates.add(
						builder.greaterThanOrEqualTo(root.get("toDate"), searchFrom)
				);
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
