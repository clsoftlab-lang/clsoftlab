package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.example.clsoftlab.entity.RetireSummary;

public class RetireSummarySpecs {

	public static Specification<RetireSummary> withPernr (List<String> pernr) {
		if (CollectionUtils.isEmpty(pernr)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(pernr);
	}
}
