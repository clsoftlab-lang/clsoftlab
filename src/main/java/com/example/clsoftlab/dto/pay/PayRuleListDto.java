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
public class PayRuleListDto {

	private Long ruleId;
    private String itemCode;
    private String itemName;
    private String ruleType;
    private String formula;
    private BigDecimal fixedValue;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String useYn;
    private String note;
    
}
