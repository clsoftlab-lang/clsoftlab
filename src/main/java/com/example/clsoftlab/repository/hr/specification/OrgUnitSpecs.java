package com.example.clsoftlab.repository.hr.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.OrgUnit;

public class OrgUnitSpecs {

	public static Specification<OrgUnit> withBizCode (String bizCode) {
		if (bizCode == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("bizCode"), bizCode);
	}
	
	public static Specification<OrgUnit> withOrgName (String orgName) {
		if (orgName == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("orgName"), "%"+orgName+"%");
	}
	
	public static Specification<OrgUnit> withUseYn (String useYn) {
		if (useYn == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
