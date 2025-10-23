package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.PayRule;

public class PayRuleSpecs {

	public static Specification<PayRule> withItemCode (List<String> itemCode) {
		if(CollectionUtils.isEmpty(itemCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("itemCode").in(itemCode);
	}
	
	public static Specification<PayRule> withRuleType (List<String> ruleType) {
		if(CollectionUtils.isEmpty(ruleType)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("ruleType").in(ruleType);
	}
	
	public static Specification<PayRule> withUseYn (String useYn) {
		if (!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
