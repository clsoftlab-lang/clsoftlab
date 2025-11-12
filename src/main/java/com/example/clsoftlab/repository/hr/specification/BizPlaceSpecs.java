package com.example.clsoftlab.repository.hr.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.BizPlace;

public class BizPlaceSpecs {

	public static Specification<BizPlace> withBizCode (List<String> bizCode) {
		if (CollectionUtils.isEmpty(bizCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("bizCode").in(bizCode);
	}
	
	public static Specification<BizPlace> withAddress (String sido, String sigungu) {
		if (!StringUtils.hasText(sido)) {
			return null;
		}
		
		if (!StringUtils.hasText(sigungu)) {
			return (root, query, builder) -> builder.equal(root.get("sido"), sido);
		} else {
			return (root, query, builder) -> builder.equal(root.get("sigungu"), sigungu);
		}
	}
	
	public static Specification<BizPlace> withUseYn (String useYn) {
		if (!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
