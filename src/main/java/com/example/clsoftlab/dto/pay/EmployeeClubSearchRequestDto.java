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
public class EmployeeClubSearchRequestDto {

	private List<String> empNo;
	private List<String> clubCode;
	private String payYn;
	private LocalDate fromDate;
	private LocalDate toDate;
}
