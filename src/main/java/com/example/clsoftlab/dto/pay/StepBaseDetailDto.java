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
public class StepBaseDetailDto {

	private long id;
    private String gradeCode;
    private Integer stepNo;
    private Long basePay;
    private String baseUnit; 
    private LocalDate fromDate;
    private LocalDate toDate;
    private String useYn;
    private String note;
}
