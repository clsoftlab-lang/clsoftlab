package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeFamilyDetailDto {
	
	private Long id;                 // 가족 ID
    
    // 사원 정보 (Flattening)
    private String employeeName;     // 사원명
    private String employeePernr;    // 사원번호
    
    // 가족 기본 정보
    private Integer familySeq;       // 가족 순번
    private String familyType;       // 관계 코드 (HR_REL_TYPE)
    private String familyName;       // 가족 성명
    private String ssn;              // 주민등록번호 
    
    private LocalDate birth;         // 생년월일
    private String gender;           // 성별
    
    private String jobType;          // 직업 구분 (HR_JOB_TYPE)
    private String schoolType;       // 학력 구분 (HR_SCH_TYPE)
    private String schoolName;       // 학교명
    private String liveYn;           // 동거 여부
    
    // 공제 및 수당 플래그
    private String dependYn;         // 부양가족 여부
    private String allowYn;          // 수당 적용 여부
    private String taxYn;            // 세금공제 여부
    private String disabledYn;       // 장애 여부
    
    // [추가] 연락처
    private String phone;            // 연락처
    
    // 기간 및 비고
    private LocalDate fromDate;      // 시작일
    private LocalDate toDate;        // 종료일
    private String note;             // 비고
    
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
