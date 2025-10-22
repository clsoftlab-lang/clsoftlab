package com.example.clsoftlab.repository.pay.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.PayDetail;

import jakarta.persistence.criteria.Predicate;

public class PayDetailSpecs {

	public static Specification<PayDetail> withPayYmRange(String payYmFrom, String payYmTo) {
		// 람다 표현식 본체에서 Predicate를 조합하여 반환합니다.
		return (root, query, builder) -> {
			
			// 여러 조건을 담을 리스트 생성
			List<Predicate> predicates = new ArrayList<>();
			
			// 1. payYmFrom 조건 (시작월)
			if (StringUtils.hasText(payYmFrom)) {
				String formattedFrom = payYmFrom.replace("-", "");
				predicates.add(builder.greaterThanOrEqualTo(root.get("payYm"), formattedFrom));
			}
			
			// 2. payYmTo 조건 (종료월)
			if (StringUtils.hasText(payYmTo)) {
				String formattedTo = payYmTo.replace("-", "");
				predicates.add(builder.lessThanOrEqualTo(root.get("payYm"), formattedTo));
			}
			
			// 3. 조건이 하나도 없으면 null (where 절에 추가 안 함)
			if (predicates.isEmpty()) {
				return null; 
			}
			
			// 4. 생성된 모든 조건을 AND로 묶어 반환
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
	public static Specification<PayDetail> withSeqNo (Integer seqNo) {
		if (seqNo == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("seqNo"), seqNo);
	}
	
	public static Specification<PayDetail> withEmpNos (List<String> empNos) {
		if (CollectionUtils.isEmpty(empNos)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employeeMaster").get("pernr").in(empNos);
	}
	
	public static Specification<PayDetail> withItemCodes (List<String> itemCodes) {
		if (CollectionUtils.isEmpty(itemCodes)) {
			return (root, query, builder) -> null;
		}
		
		return (root, query, builder) -> root.get("payItem").get("itemCode").in(itemCodes);
	}
}
