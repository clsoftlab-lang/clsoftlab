package com.example.clsoftlab.dto.pay;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeClubDetailDto {

	private Long id;
	private Long clubId;
    private String employeeName;
    private String employeePernr;
    private String clubCode;
    private String clubName;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String payYn;
    private String note;
}
