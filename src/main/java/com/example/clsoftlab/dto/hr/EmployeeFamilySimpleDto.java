package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFamilySimpleDto {

    private String familyType;    
    private String familyName;      
    private LocalDate birthDate;    
    private String jobType;
    private Integer familySeq;
    
}
