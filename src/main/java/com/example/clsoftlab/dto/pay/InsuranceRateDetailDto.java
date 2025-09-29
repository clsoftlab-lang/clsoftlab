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
public class InsuranceRateDetailDto {

	private Long id;
    private String insType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private BigDecimal pcEmp;
    private BigDecimal pcCmp;
    private String note;
    private String useYn;
}
