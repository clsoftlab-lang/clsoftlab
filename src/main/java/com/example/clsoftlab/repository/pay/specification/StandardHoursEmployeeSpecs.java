package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.StandardHoursEmployee;

public class StandardHoursEmployeeSpecs {

	public static Specification<StandardHoursEmployee> withCalYm (String calYm) {
		if (!StringUtils.hasText(calYm)) {
			return null;
		}
		
		String formmatedCalYm = calYm.replace("-", "");
		return (root, query, builder) -> builder.equal(root.get("calYm"), formmatedCalYm);
	}
	
	public static Specification<StandardHoursEmployee> withEmpNo (List<String> empNo) {
		if (CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
}
