package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.GlMappingRule;

public class GlMappingRuleSpecs {

	public static Specification<GlMappingRule> withItemCode (List<String> itemCode) {
		if (CollectionUtils.isEmpty(itemCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("payItem").get("itemCode").in(itemCode);
	}
	
	public static Specification<GlMappingRule> withBizCode (List<String> bizCode) {
		if (CollectionUtils.isEmpty(bizCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("bizCode").in(bizCode);
	}
	
	public static Specification<GlMappingRule> withCostCntr (List<String> costCntr) {
		if (CollectionUtils.isEmpty(costCntr)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("costCntr").in(costCntr);
	}
	
	public static Specification<GlMappingRule> withUseYn (String useYn) {
		if (!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
