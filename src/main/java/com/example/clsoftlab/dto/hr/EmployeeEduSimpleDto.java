package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEduSimpleDto {

	private Long id;            // 상세 조회/수정 시 Key로 사용
    private Integer seq;        // 순번 (정렬용)
    private String eduType;     // 교육 유형 (사내/사외 - 공통코드 매핑용)
    private String eduName;     // 교육 과정명 (가장 중요)
    private String eduOrg;      // 교육 기관
    private LocalDate startDate;// 시작일
    private LocalDate endDate;  // 종료일
    private Integer hour;       // 교육 시간 (n시간)
    private String mandatoryYn; // 필수 여부 (Badge 표시용)
    private String completeYn;  // 수료 여부 (상태 표시용)
    
}
