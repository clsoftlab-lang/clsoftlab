package com.example.clsoftlab.dto.hr;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEduDetailDto {

	private Long id;
    private String pernr;
    private Integer seq;
    private String eduType;
    private String eduName;
    private String eduOrg;
    private LocalDate startDate;
    private LocalDate endDate;
    private String completeYn;
    private String mandatoryYn; 
    private Integer eduScore;   
    private Integer hour;
    private BigDecimal cost;
    private String attachId;
    private String remark;
}
