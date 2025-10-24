package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.PayCycle;

public class PayCycleSpecs {

	public static Specification<PayCycle> withJobGroup (List<String> jobGroup) {
		if (CollectionUtils.isEmpty(jobGroup)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("jobGroup").in(jobGroup);
	}
	
	public static Specification<PayCycle> withUseYn (String useYn) {
		if (!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
