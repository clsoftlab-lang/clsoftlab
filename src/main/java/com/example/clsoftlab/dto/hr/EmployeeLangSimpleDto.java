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
public class EmployeeLangSimpleDto {

	private Long id;              // PK
    private Integer seq;          // 정렬 순서
    private String langCode;      // 언어 (HR_LANG)
    private String testCode;      // 시험 (HR_LANG_TEST)
    private String testName;      // 시험명 (기타 입력 시)
    private String score;         // 점수/등급
    private LocalDate getDate;    // 취득일
    private LocalDate expDate;    // 만료일
    private String speakLvl;      // 회화 수준
    private String readLvl;       // 독해 수준
}
