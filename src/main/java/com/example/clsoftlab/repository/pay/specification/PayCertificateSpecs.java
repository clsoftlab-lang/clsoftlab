package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.PayCertificate;

public class PayCertificateSpecs {

	public static Specification<PayCertificate> withYear (String year) {
		if (!StringUtils.hasText(year)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("year"), year);
	}
	
	public static  Specification<PayCertificate> withEmpNo (List<String> empNo) {
		if (CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
	
	public static Specification<PayCertificate> withPeriodType (List<String> periodType) {
		if (CollectionUtils.isEmpty(periodType)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("periodType").in(periodType);
	}
}
