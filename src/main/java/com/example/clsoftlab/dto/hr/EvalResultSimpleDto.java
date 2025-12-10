package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvalResultSimpleDto {

	private Long resultId;
    private String templateName;  // 평가명 (예: 2024 상반기 업적평가)
    private String templateYear;          // 평가 연도 (예: 2024)
    private String templateEvType;      // 평가 유형 코드 (예: ACH, CMP -> 화면에서 공통코드로 변환)
    private String evalGrade;     // 최종 등급 (S, A, B...)
    private Integer totalScore;   // 최종 점수 (필요 시 표시, 보통 등급만 보여주기도 함)
    private String status;        // 진행 상태 (T:임시, C:제출/완료)
    private String evalStep;      // 현재 단계 (900: 확정 등) -> '완료' 여부 판단용
}

