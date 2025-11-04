package com.example.clsoftlab.dto.pay;

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
public class EmployeeWithholdRateDetailDto {

	private Long id;
    private String employeeName;
    private String employeePernr;
    private BigDecimal withholdPc;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String note;
}
