package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEduHistorySimpleDto {

	private Long id;            // 수정/삭제를 위한 Key
    private String school;      // 학교명 (가장 중요한 정보)
    private String country;     // 국가 (코드)
    private String degree;      // 학위 (학사, 석사 등 - 코드)
    private String status;      // 졸업상태 (졸업, 수료 등 - 코드)
    private String finalYn;     // 최종학력 여부 (배지 표시용)
}
