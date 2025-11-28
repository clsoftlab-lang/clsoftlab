package com.example.clsoftlab.dto.common;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeMasterDto {

	private String pernr;
    private String name;
    private LocalDate entryDate;
    private LocalDate retireDate;
    private String deptCode;
    private String rankCode;
    private String dutyCode;
    private String empStatus;
}