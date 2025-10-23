package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.example.clsoftlab.entity.BackpayRule;

public class BackpayRuleSpecs {

	public static Specification<BackpayRule> withAppliedItemCode (List<String> appliedItemCode) {
		if (CollectionUtils.isEmpty(appliedItemCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("appliedItem").get("itemCode").in(appliedItemCode);
	}
	
	public static Specification<BackpayRule> withBaseItemCode (List<String> baseItemCode) {
		if (CollectionUtils.isEmpty(baseItemCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("baseItem").get("itemCode").in(baseItemCode);
	}
}
