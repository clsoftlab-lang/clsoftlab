package com.example.clsoftlab.repository.pay.specification;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.StepBase;

public class StepBaseSpecs {

	 public static Specification<StepBase> withGradeCode(String gradeCode) {
	        if (gradeCode == null) {
	            return null;
	        }
	        return (root, query, builder) -> builder.like(root.get("gradeCode"), "%" + gradeCode + "%");
	    }
	 
    public static Specification<StepBase> withStepNo(Integer stepNo) { 
        if (stepNo == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get("stepNo"), stepNo);
    }

    public static Specification<StepBase> withUseYn(String useYn) {
        if (useYn == null) {
            return null;
        }
        return (root, query, builder) -> builder.like(root.get("useYn"), "%" + useYn + "%");
    }
}
