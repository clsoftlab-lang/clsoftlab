package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.ClubItem;

public class ClubItemSpecs {

	public static Specification<ClubItem> withClubCode (List<String> clubCode) {
		if (CollectionUtils.isEmpty(clubCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("clubCode").in("clubCode");
	}
	
	public static Specification<ClubItem> withUseYn (String useYn) {
		if (!StringUtils.hasText(useYn)) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("useYn"), useYn);
	}
}
