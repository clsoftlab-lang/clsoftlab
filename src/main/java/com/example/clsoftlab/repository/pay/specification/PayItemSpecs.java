package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.PayItem;

public class PayItemSpecs {

	public static Specification<PayItem> withItemName (String itemName) {
		if (itemName == null || itemName.isEmpty()) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("itemName"), "%"+itemName+"%");
	}
	
	public static Specification<PayItem> withItemTypes (List<String> itemTypes) {
		if(itemTypes == null || itemTypes.isEmpty()) {
			return null;
		}
		
		return (root, query, builder) -> root.get("itemType").in(itemTypes);
	}
	
	public static Specification<PayItem> withUseYn (String useYn) {
		if(useYn == null || useYn.isEmpty()) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
