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
public class RetireSummaryDetailDto {

	private Long id;
    private String employeePernr;
	private String employeeName;
    private LocalDate entryDate;
    private LocalDate retireDate;
    private BigDecimal serviceYears;
    private BigDecimal avgSalary;
    private BigDecimal retirePay;
    private BigDecimal taxAmount;
    private BigDecimal finalPay;
    private String note;
}
