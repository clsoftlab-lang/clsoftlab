package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.CalcOrder;

public class CalcOrderSpecs {

	public static Specification<CalcOrder> withItemCode (List<String> itemCode) {
		if(CollectionUtils.isEmpty(itemCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("payItem").get("itemCode").in(itemCode);
	}
	
	public static Specification<CalcOrder> withGroupCode (List<String> groupCode) {
		if(CollectionUtils.isEmpty(groupCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("groupCode").in(groupCode);
	}
	
	public static Specification<CalcOrder> withUseYn (String useYn) {
		if(!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
