package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.StepBase;

public class StepBaseSpecs {

	 public static Specification<StepBase> withGradeCode(List<String> gradeCode) {
	        if (CollectionUtils.isEmpty(gradeCode)) {
	            return null;
	        }
	        return (root, query, builder) -> root.get("gradeCode").in(gradeCode);
	    }
	 
    public static Specification<StepBase> withStepNo(Integer stepNo) { 
        if (stepNo == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get("stepNo"), stepNo);
    }

    public static Specification<StepBase> withUseYn(String useYn) {
        if (!StringUtils.hasText(useYn)) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
    }
}
