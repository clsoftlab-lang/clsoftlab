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
public class EmployeeInfoDetailDto {

	private String pernr;
    private LocalDate hireDate;
    private String empType;
    private String workType;
    private String bizCode;
    private String deptCode;
    private String posCode;
    private String gradeCode;
    private LocalDate deptJoin;
    private String empStatus;
    private LocalDate lastApptDate;
}
