package com.example.clsoftlab.repository.hr.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.EmployeeFamily;

public class EmployeeFamilySpecs {

	public static Specification<EmployeeFamily> withEmpNo (List<String> empNo) {
		if(CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
	
	public static Specification<EmployeeFamily> withFamilyName (String familyName) {
		if(!StringUtils.hasText(familyName)) {
			return null;
		}
		
		return (root, query, builder) -> builder.like(root.get("familyName"), "%"+familyName+"%");
	}
	
	public static Specification<EmployeeFamily> withFamilyType (List<String> familyType) {
		if (CollectionUtils.isEmpty(familyType)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("familyType").in(familyType);
	}
	
}
