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
    private String deptCode;
    private String rankCode;
    private String dutyCode;
    private LocalDate entryDate;
    private String empStatus;
    private String gender;
    private LocalDate birthDate; 
    
    private String ssn;         
    private String nationCode;  
    
    private String phoneNo;
    private String homeTel;     
    private String email;
    
    // --- 주소 세분화 (addr -> 4개 필드) ---
    private String zipCode;
    private String sido;
    private String sigungu;
    private String addrMain;
    private String addrDetail;
    // -----------------------------------

    private String maritalCode; 
    private String militaryCode; 
    private String disabilityYn;
    
    private String emergencyName;
    private String emergencyRel;
    private String emergencyPhone;
    
    public void setSsn (String ssn) {
    	this.ssn = maskSsn(ssn);
    }
    
    private String maskSsn(String ssn) {
        if (ssn == null) return null;
        
        String cleanSsn = ssn.replace("-", "");
        
        if (cleanSsn.length() < 7) {
            return ssn; 
        }
        
        return cleanSsn.substring(0, 6) + "-" + cleanSsn.charAt(6) + "******";
    }
}
