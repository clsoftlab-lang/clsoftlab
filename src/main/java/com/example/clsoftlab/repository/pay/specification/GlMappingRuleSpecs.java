package com.example.clsoftlab.repository.pay.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.GlMappingRule;

public class GlMappingRuleSpecs {

	public static Specification<GlMappingRule> withItemCode (String itemCode) {
		if (itemCode == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("itemCode"), "%"+itemCode+"%");
	}
	
	public static Specification<GlMappingRule> withBizCode (String bizCode) {
		if (bizCode == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("bizCode"), "%"+bizCode+"%");
	}
	
	public static Specification<GlMappingRule> withCostCntr (String costCntr) {
		if (costCntr == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("costCntr"), "%"+costCntr+"%");
	}
	
	public static Specification<GlMappingRule> withUseYn (String useYn) {
		if (useYn == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("useYn"), "%"+useYn+"%");
	}
}
