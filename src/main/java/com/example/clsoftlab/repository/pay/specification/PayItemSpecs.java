package com.example.clsoftlab.repository.pay.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.PayItem;

public class PayItemSpecs {

	public static Specification<PayItem> withItemName (String itemName) {
		if (itemName == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("itemName"), "%"+itemName+"%");
	}
	
	public static Specification<PayItem> withItemType (String itemType) {
		if(itemType == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("itemType"), itemType);
	}
	
	public static Specification<PayItem> withUseYn (String useYn) {
		if(useYn == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
