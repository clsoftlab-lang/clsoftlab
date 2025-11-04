package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.BankAccount;

public class BankAccountSpecs {

	public static Specification<BankAccount> withEmpNo (List<String> empNo) {
		if (CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
	
	public static Specification<BankAccount> withAccountType (List<String> accountType) {
		if (CollectionUtils.isEmpty(accountType)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("accountType").in(accountType);
	}
	
	public static Specification<BankAccount> withUseYn (String useYn) {
		if (!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
