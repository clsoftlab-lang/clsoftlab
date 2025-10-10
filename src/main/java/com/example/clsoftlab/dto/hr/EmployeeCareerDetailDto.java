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
    private String careerType;
    private String compName;
    private String dept;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer periodMonth;
    private String resignReason;
    private String attachId;
    private String remark;
    
}
