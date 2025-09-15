package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;

import com.example.clsoftlab.entity.id.TaxTableId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TaxTableDetailDto {

	private TaxTableId id;
    private BigDecimal taxPercent;
    private BigDecimal localPercent;
    private BigDecimal totalTax;
    private String useYn;
    private String note;
}
