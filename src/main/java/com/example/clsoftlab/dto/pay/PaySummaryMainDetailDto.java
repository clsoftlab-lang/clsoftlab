package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaySummaryMainDetailDto {

	private Long id;
    private String employeeName;
    private String employeePernr;
    private String payYm;
    private Integer seqNo;
    private Long totalPay;
    private Long totalDeduct;
    private Long totalReal;
    private String isFinal;
    private String note;
}
