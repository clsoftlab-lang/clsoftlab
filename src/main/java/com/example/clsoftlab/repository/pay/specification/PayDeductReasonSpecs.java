package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.PayDeductReason;

public class PayDeductReasonSpecs {

	public static Specification<PayDeductReason> withEmpNo (List<String> empNo) {
		if (CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
	
	public static Specification<PayDeductReason> withPayYm (String payYm) {
		if (!StringUtils.hasText(payYm)) {
			return null;
		}
		
		String formattedPayYm = payYm.replace("-", "");
		
		return (root, query, builder) -> builder.equal(root.get("payYm"), formattedPayYm);
	}
	
	public static Specification<PayDeductReason> withItemCode (List<String> itemCode) {
		if (CollectionUtils.isEmpty(itemCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("payItem").get("itemCode").in(itemCode);
	}
}
