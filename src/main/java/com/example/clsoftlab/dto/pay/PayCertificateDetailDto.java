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
public class PayCertificateDetailDto {

	private Long id;
	private String certNo;
    private String year;
    private String empNo;
    private String periodType;
    private LocalDate periodFrom;
    private LocalDate periodTo;
    private Long totalGross;
    private Long totalTaxable;
    private Long totalNontax;
    private Long totalTax;
    private Long totalLocalTax;
    private Long totalInsurance;
    private String insuranceDetail;
    private Integer payCount;
    private String note;
}
