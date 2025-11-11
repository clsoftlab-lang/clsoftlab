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
public class LogPayCalcDetailDto {

	private Long id;
    private String payYm;
    private Integer seqNo;
    private String empNo;
    private String name;
    private String pernr;
    private String itemName;
    private String itemCode;
    private Integer stepNo;
    private String stepDesc;
    private BigDecimal baseVal;
    private String formula;
    private BigDecimal resultVal;
    private BigDecimal roundVal;
    private String roundType;
    private String calcSrc;
    private String note;
}
