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
public class EmployeeAllowDetailDto {

	private Long id;
    private String employeeMasterPernr;
    private String employeeMasterName;
    private String PayItemCode; 
    private String PayItemName; 
    private LocalDate fromDate;
    private LocalDate toDate;
    private Long allowAmt;
    private String useYn;
    private String note;
}
