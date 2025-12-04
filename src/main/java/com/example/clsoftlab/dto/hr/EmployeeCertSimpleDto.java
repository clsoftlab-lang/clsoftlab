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
public class EmployeeCertSimpleDto {

	private Long id;              // 상세 조회/수정용 PK
    private Integer seq;          // 정렬 순서
    
    private String certCode;      // 코드 (화면 매핑용)
    private String certName;      // 자격증명 (직접입력 값 또는 Fallback)
    
    private String certRank;      // 등급 (기사, 1급 등)
    private BigDecimal certScore; // 점수 (어학 성적용)
    private String certOrg;       // 발급기관
    
    private LocalDate getDate;    // 취득일
    private LocalDate expDate;    // 만료일 (없으면 화면에서 '-' 처리)
    
    private String allowYn;       // 수당 지급 여부
    private String confirmYn;     // 담당자 확인 여부 (미확인 시 강조 표시용)
}
