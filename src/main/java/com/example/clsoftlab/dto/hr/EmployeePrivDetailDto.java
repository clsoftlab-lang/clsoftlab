package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
    private String addrDetail;
    private String maritalStatus;
    private String militaryInfo;
    private String disabilityYn;
    private String emergencyName;
    private String emergencyRel;
    private String emergencyPhone;
    
    public void setSsn (String ssn) {
    	this.ssn = maskSsn(ssn);
    }
    
    private String maskSsn(String ssn) {
        if (ssn == null) {
            return null;
        }
        StringBuilder maskedAccount = new StringBuilder(ssn);

        // 123456 - 1******
        for (int i = 8; i <= 13; i++) {
            maskedAccount.setCharAt(i, '*');
        }

        return maskedAccount.toString();
    }
}
