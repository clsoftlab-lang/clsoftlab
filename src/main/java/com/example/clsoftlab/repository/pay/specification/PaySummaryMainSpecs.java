package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.PaySummaryMain;

public class PaySummaryMainSpecs {

	public static Specification<PaySummaryMain> withEmpNo (List<String> empNo) {
		if (CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
	
	public static Specification<PaySummaryMain> withPayYm (String payYm) {
		if (!StringUtils.hasText(payYm)) {
			return null;
		}
		
		String formattedPayYm = payYm.replace("-", "");
		
		return (root, query, builder) -> builder.equal(root.get("payYm"), formattedPayYm);
	}
	
	public static Specification<PaySummaryMain> withIsFinal (String isFinal) {
		if (!StringUtils.hasText(isFinal)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("isFinal"), isFinal);
	}
}
