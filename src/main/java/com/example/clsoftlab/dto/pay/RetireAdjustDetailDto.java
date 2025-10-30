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
public class RetireAdjustDetailDto {

	private Long id;
	private String retireSummaryEmployeePernr;
	private String retireSummaryEmployeeName;
	private LocalDate retireSummaryRetireDate;
    private String adjType;
    private String adjReason;
    private BigDecimal adjAmount;
    private String adjExpr;
    private String note;
}
