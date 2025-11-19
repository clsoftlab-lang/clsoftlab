package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.PayItem;

public class PayItemSpecs {

	public static Specification<PayItem> withItemCode (List<String> itemCode) {
		if (CollectionUtils.isEmpty(itemCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("itemCode").in(itemCode);
	}
	
	public static Specification<PayItem> withItemTypes (List<String> itemTypes) {
		if(CollectionUtils.isEmpty(itemTypes)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("itemType").in(itemTypes);
	}
	
	public static Specification<PayItem> withUseYn (String useYn) {
		if(!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
