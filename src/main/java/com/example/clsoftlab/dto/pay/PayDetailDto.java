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
public class PayDetailDto {

	private Long id;
    private String payYm;
    private Integer seqNo;
    private String employeeMasterPernr; 
    private String employeeMasterName; 
    private String itemCode; 
    private String itemName;
    private BigDecimal amount;
    private String backYm;
    private BigDecimal origAmt; 
    private String note;
    private String createdBy;
    private LocalDate createdAt;
}
