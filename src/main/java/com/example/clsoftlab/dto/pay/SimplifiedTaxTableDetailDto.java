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
public class SimplifiedTaxTableDetailDto {

	private Long id;
    private String year;
    private Integer familyCount; 
    private Long incomeAmt; 
    private BigDecimal taxPc; 
    private BigDecimal localPc; 
    private Long totalTax; 
    private String useYn; 
    private String note; 
}
