package com.example.clsoftlab.dto.pay;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
public class EmployeeStepDetailDto {

	private Long id;
    private String employeePernr; 
    private String employeeName;
    private String gradeCode; // 직군 코드 (ZGRADE_CD)
    private Integer stepNo; // 호봉 번호 (ZSTEP_NO)
    private LocalDate fromDate; // 적용 시작일 (ZFROM_DT)
    private LocalDate toDate; // 적용 종료일 (ZTO_DT)
    private String note;
}
