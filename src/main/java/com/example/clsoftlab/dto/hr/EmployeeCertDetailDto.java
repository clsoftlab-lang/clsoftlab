package com.example.clsoftlab.dto.hr;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCertDetailDto {

	private Long id;
    private String pernr;
    private Integer seq;
    
    private String certCode;      // 자격증 코드 (HR_CERT_CD)
    private String certName;      // 자격증명
    private String certRank;      // 등급 (HR_CERT_RANK)
    private BigDecimal certScore; // 취득 점수
    
    private String certOrg;       // 발급기관
    private String certNo;        // 자격증 번호
    
    private LocalDate getDate;    // 취득일
    private LocalDate expDate;    // 만료일
    
    private String jobRel;        // 직무 연관성
    private String allowYn;       // 수당 지급 여부
    private String confirmYn;     // 담당자 승인 여부
    
    private String remark;        // 비고
    private String attachId;      // 첨부파일 ID
}
