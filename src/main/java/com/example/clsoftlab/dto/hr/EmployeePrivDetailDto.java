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
public class EmployeePrivDetailDto {

    private String pernr;
    private String name;
    private String gender;
    private LocalDate birthdate;
    private String ssn;
    private String nationality;
    private String phoneNo;
    private String email;
    private String addr;
    private String maritalStatus;
    private String militaryInfo;
    private String disabilityYn;
    private String emergencyName;
    private String emergencyRel;
    private String emergencyPhone;
}
