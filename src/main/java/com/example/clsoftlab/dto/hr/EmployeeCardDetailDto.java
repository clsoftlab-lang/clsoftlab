package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeCardDetailDto {

    private String pernr; // 사번 (PK)
    private String name; // 성명
    private LocalDate birth; // 생년월일
    private String sex; // 성별 (M/F)
    private String nation; // 국적
    private String rrn; // 주민등록번호 (암호화 전 평문)
    private String phone; // 휴대전화
    private String email; // 이메일
    private String addr; // 주소 및 상세주소
    private String emerPhone; // 비상연락처
    private LocalDate joinDate; // 입사일
    private LocalDate firstJoinDate; // 최초입사일
    private String joinType; // 입사유형 (코드)
    private String joinPath; // 입사경로 (코드)
    private String contractType; // 고용형태/계약구분 (코드)
    private String probationPeriod; // 수습기간
    private String joinCategory; // 입사형태 (코드)
    private String workStatus; // 재직상태 (코드)
    private String protectName; // 보호자 성명
    private String protectRel; // 보호자 관계
    private String protectPhone; // 보호자 연락처
    private String protectAddr; // 보호자 주소
    private String milServiceStatus; // 병역구분 (코드)
    private String milType; // 병역종류
    private LocalDate milPeriodStart; // 병역 시작일
    private LocalDate milPeriodEnd; // 병역 종료일
    private String vetYn; // 보훈대상여부 (Y/N)
    private String disabilityYn; // 장애등록여부 (Y/N)
    
    public void setRrn (String rrn) {
    	this.rrn = maskRrn(rrn);
    }
    
    private String maskRrn(String rrn) {
        if (rrn == null) {
            return null;
        }
        StringBuilder maskedAccount = new StringBuilder(rrn);

        // 123456 - 1******
        for (int i = 8; i <= 13; i++) {
            maskedAccount.setCharAt(i, '*');
        }

        return maskedAccount.toString();
    }
}
