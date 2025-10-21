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
public class PayDetailDto {

	private Long id;
    private String payYm;
    private Integer seqNo;
    private String pernr; 
    private String itemCode; 
    private BigDecimal amount;
    private String backYm;
    private BigDecimal origAmt; 
    private String note;
}
