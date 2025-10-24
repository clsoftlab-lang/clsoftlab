package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.example.clsoftlab.entity.BasePay;

public class BasePaySpecs {

	public static Specification<BasePay> withEmpNo (List<String> empNo) {
		if (CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employeeMaster").get("pernr").in(empNo);
	}
	
	public static Specification<BasePay> withBaseUnit (List<String> baseUnit) {
		if (CollectionUtils.isEmpty(baseUnit)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("baseUnit").in(baseUnit);
	}
}
