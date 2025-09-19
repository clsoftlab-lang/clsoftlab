package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayDeductReasonDetailDto {

	private long id;
    private String empNo;
    private String payYm;
    private Integer seqNo;
    private String itemCode;
    private BigDecimal days;
    private BigDecimal hours;
    private Long amount;
    private String reasonCode;
    private String note;
	
}
