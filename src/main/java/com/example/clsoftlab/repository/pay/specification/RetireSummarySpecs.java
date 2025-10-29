package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.RetireSummary;

public class RetireSummarySpecs {

	public static Specification<RetireSummary> withPernr (List<String> pernr) {
		if (CollectionUtils.isEmpty(pernr)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(pernr);
	}
}
