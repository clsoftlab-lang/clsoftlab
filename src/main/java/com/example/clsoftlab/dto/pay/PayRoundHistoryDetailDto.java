package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRoundHistoryDetailDto {

	private long id;
    private String employeePernr;
    private String employeeName;
    private String payYm;
    private String payItemCode;
    private String payItemName;
    private Long rawAmount;
    private String roundType;
    private Long roundAmount;
    private Long diffAmount;
    private String sourcePayItemCode;
    private String sourcePayItemName;
    private String note;
}
