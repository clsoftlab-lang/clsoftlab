package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.example.clsoftlab.entity.EmployeeFamily;

public class EmployeeFamilySpecs {

	public static Specification<EmployeeFamily> withEmpNo (List<String> empNo) {
		if(CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
	
	
}
