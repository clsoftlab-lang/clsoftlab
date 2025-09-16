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
public class BackpayRuleDetailDto {

	private long ruleId;
    private String appliedItemCode;
    private String baseItemCode;
    private String ruleType;
    private BigDecimal backPercent;
    private String useYn;
    private String note;
}
