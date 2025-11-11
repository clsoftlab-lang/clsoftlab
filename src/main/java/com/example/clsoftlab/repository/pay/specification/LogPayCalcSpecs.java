package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.LogPayCalc;

public class LogPayCalcSpecs {

	public static Specification<LogPayCalc> withPayYm (List<String> payYm) {
		if(CollectionUtils.isEmpty(payYm)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("payYm").in(payYm);
	}
	
	public static Specification<LogPayCalc> withEmpNo (List<String> empNo) {
		if(CollectionUtils.isEmpty(empNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("employee").get("pernr").in(empNo);
	}
	
	public static Specification<LogPayCalc> withItemCode (List<String> itemCode) {
		if(CollectionUtils.isEmpty(itemCode)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("payItem").get("itemCode").in(itemCode);
	}
	
	public static Specification<LogPayCalc> withSeqNo (List<Integer> seqNo) {
		if(CollectionUtils.isEmpty(seqNo)) {
			return null;
		}
		
		return (root, query, builder) -> root.get("seqNo").in(seqNo);
	}
}
