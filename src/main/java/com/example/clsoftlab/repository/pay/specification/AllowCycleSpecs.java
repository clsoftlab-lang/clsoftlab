package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.AllowCycle;

public class AllowCycleSpecs {

	public static Specification<AllowCycle> withItemCode (List<String> itemCode) {
		if (CollectionUtils.isEmpty(itemCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("payItem").get("itemCode").in(itemCode);
	}
	
	public static Specification<AllowCycle> withCycle (List<String> cycle) {
		if (CollectionUtils.isEmpty(cycle)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("cycle").in(cycle);
	}
	
	public static Specification<AllowCycle> withUseYn (String useYn) {
		if (!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
