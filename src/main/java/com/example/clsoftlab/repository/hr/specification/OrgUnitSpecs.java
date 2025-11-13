package com.example.clsoftlab.repository.hr.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.OrgUnit;

public class OrgUnitSpecs {

	public static Specification<OrgUnit> withBizCode (List<String> bizCode) {
		if (CollectionUtils.isEmpty(bizCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("bizPlace").get("bizCode").in(bizCode);
	}
	
	public static Specification<OrgUnit> withOrgName (List<String> orgName) {
		if (CollectionUtils.isEmpty(orgName)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("orgName").in(orgName);
	}
	
	public static Specification<OrgUnit> withUseYn (String useYn) {
		if (!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
