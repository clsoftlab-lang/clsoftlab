package com.example.clsoftlab.repository.pay.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.clsoftlab.entity.StandardHours;

import jakarta.persistence.criteria.Predicate;

public class StandardHoursSpecs {

	public static Specification<StandardHours> withCalYm (String calYm) {
		if (!StringUtils.hasText(calYm)) {
			return null;
		}
		
		String calYmForSearch = calYm.replace("-", "");
		
		return (root, query, builder) -> builder.equal(root.get("calYm"), calYmForSearch);
	}
	
	public static Specification<StandardHours> withJobGroup (List<String> jobGroup) {
		if (CollectionUtils.isEmpty(jobGroup)) {
			return null;
		}
		
				// '전체'(빈 문자열 "")가 검색 조건에 포함되어 있는지 확인
				boolean searchForNull = jobGroup.contains("");

				// 빈 문자열을 제외한 실제 직군 코드 리스트를 필터링
				List<String> actualJobGroups = jobGroup.stream()
						.filter(StringUtils::hasText) // null과 ""(빈 문자열)을 모두 제거
						.toList();

				return (root, query, builder) -> {
					
					// OR 조건으로 묶을 Predicate 리스트 생성
					List<Predicate> predicates = new ArrayList<>();

					// 1. 실제 직군 코드가 있다면: IN (...) 조건 추가
					if (!CollectionUtils.isEmpty(actualJobGroups)) {
						predicates.add(root.get("jobGroup").in(actualJobGroups));
					}

					// 2. '전체' 검색이 포함되었다면: IS NULL 조건 추가
					if (searchForNull) {
						predicates.add(builder.isNull(root.get("jobGroup")));
					}

					// 3. 생성된 조건들을 OR로 묶어서 반환
					return builder.or(predicates.toArray(new Predicate[0]));
				};
		
	}
}
