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
public class EmployeeCareerSimpleDto {

	private Long id;              // 수정/삭제용 PK
    private Integer seq;          // 정렬 순서
    private String careerType;    // 근무 형태 (코드: 10, 20...)
    private String companyName;   // 회사명
    private String deptName;      // 부서명 (툴팁이나 서브 텍스트용)
    private String jobRank;       // 직위 (과장, 대리 등)
    private String jobDuty;       // 직무 (서버개발, 인사기획 등)
    private LocalDate startDate;  // 입사일
    private LocalDate endDate;    // 퇴사일
    private String totalPeriod;   
    private Integer recogRatio;   // 인정 비율 (100%가 아니면 강조 표시용)
}
