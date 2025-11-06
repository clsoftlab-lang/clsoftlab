package com.example.clsoftlab.repository.pay.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.EmployeeClub;

import jakarta.persistence.criteria.Predicate;

public class EmployeeClubSpecs {

	
	// empNo 조건
	public static Specification<EmployeeClub> withEmpNo (List<String> empNo) {
		if(CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
	
	// clubCode 조건
	public static Specification<EmployeeClub> withClubCode (List<String> clubCode) {
		if(CollectionUtils.isEmpty(clubCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("clubItem").get("clubCode").in(clubCode);
	}
	
	public static Specification<EmployeeClub> withPayYn (String payYn) {
		if(!StringUtils.hasText(payYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("payYn"), payYn);
	}
	
	// 기간 조건
	public static Specification<EmployeeClub> withDateRange(LocalDate fromDate, LocalDate toDate) {

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
