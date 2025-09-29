package com.example.clsoftlab.repository.hr.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.BizPlace;

public class BizPlaceSpecs {

	public static Specification<BizPlace> withBizCode (String bizCode) {
		if (bizCode == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("bizCode"), "%"+bizCode+"%");
	}
	
	public static Specification<BizPlace> withBizName (String bizName) {
		if (bizName == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("bizName"), "%"+bizName+"%");
	}
	
	public static Specification<BizPlace> withAddress (String address) {
		if (address == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("address"), "%"+address+"%");
	}
	
	public static Specification<BizPlace> withUseYn (String useYn) {
		if (useYn == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
