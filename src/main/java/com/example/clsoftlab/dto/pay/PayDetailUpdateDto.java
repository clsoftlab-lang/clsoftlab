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
public class PayDetailUpdateDto {

	public Long id;
	private BigDecimal amount;
    private String backYm;
    private BigDecimal origAmt; 
    private String note;
}
