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
public class EmployeeCareerDetailDto {

	private Long id;
    private String pernr;
    private Integer seq;
    private String careerType;    // 근무 형태
    private String companyName;   // 회사명 (compName -> companyName)
    private String deptName;      // 부서명 (dept -> deptName)
    private String jobRank;       // 직위/직급
    private String jobDuty;       // 담당 직무
    private String jobDesc;       // 업무 상세
    private LocalDate startDate;
    private LocalDate endDate;
    private Long totalMonths;     // 근무 개월 수
    private Long lastSalary;      // 최종 연봉
    private Integer recogRatio;   // 경력 인정 비율
    private String resignReason;
    private String attachId;
    private String remark;
    private Long version;
    
}
