package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.ClubPayDetail;

public class ClubPayDetailSpecs {

	public static Specification<ClubPayDetail> withEmpNo (List<String> empNo) {
		if (CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
	
	public static Specification<ClubPayDetail> withPayYm (String payYm) {
		if (!StringUtils.hasText(payYm)) {
			return null;
		}
		
		String formattedPayYm = payYm.replace("-", "");
		
		return (root, query, builder) -> builder.equal(root.get("payYm"), formattedPayYm);
	}
	
	public static Specification<ClubPayDetail> withClubCode (List<String> clubCode) {
		if (CollectionUtils.isEmpty(clubCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("clubItem").get("clubCode").in(clubCode);
	}
}
