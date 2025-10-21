package com.example.clsoftlab.repository.pay.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.clsoftlab.entity.PayDetail;

public class PayDetailSpecs {

	public static Specification<PayDetail> withPayYm (String payYm) {
		if (payYm == null || payYm.isEmpty()) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("payYm"), payYm);
	}
	
	public static Specification<PayDetail> withSeqNo (Integer seqNo) {
		if (seqNo == null) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("seqNo"), seqNo);
	}
	
	public static Specification<PayDetail> withEmpNo (String empNo) {
		if (empNo == null || empNo.isEmpty()) {
			return null;
		}
		
		return (root, query, builder) -> builder.equal(root.get("employeeMaster").get("pernr"), empNo);
	}
	
	public static Specification<PayDetail> withItemCode (List<String> itemCode) {
		if (itemCode == null || itemCode.isEmpty()) {
			return null;
		}
		
		return (root, query, builder) -> root.in(itemCode); 
	}
}
