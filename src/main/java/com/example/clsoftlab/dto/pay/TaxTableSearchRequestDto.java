package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxTableSearchRequestDto {

	private Integer year;
	private Integer familyCount;
	private Long incomeFrom;
	private Long incomeTo;
}
