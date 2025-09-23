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
public class EmployeeFamilyDetailDto {
	
	private long id;
    private String empNo;
    private Integer familySeq;
    private String familyType;
    private String familyName;
    private LocalDate birth;
    private String gender;
    private String dependYn; 
    private String allowYn;
    private String taxYn;
    private String disabledYn;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String note;

}
