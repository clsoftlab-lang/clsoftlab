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
public class BasePayDetailDto {

	private long payId;
    private String empNo;
    private LocalDate fromDate;
    private LocalDate toDate;
    private BigDecimal basePay;
    private String baseUnit;
    private Integer standardHours;
    private String note;
}
