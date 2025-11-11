package com.example.clsoftlab.dto.pay;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceRateSearchDto {

	private List<String> insType;
	private LocalDate fromDate;
	private String useYn;
	
}
