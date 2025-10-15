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
public class EmployeePersonalDetailDto {

    private String pernr;
    private LocalDate joinDate;
    private String empType;
    private String locCode;
    private String deptCode;
    private String gradeCode;
    private String posCode;
    private String status;
    private String ruleId;
    private LocalDate effDate;
    private String remark;
}
