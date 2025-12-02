package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointHistDetailDto {

	// [이력 기본 정보]
    private String id;          // ZHIST_ID
    private LocalDate effDate;  // ZEFF_DATE (시행일)
    private String remark;      // ZREMARK
    private String isCanceled;  // IS_CANCELED

    // [사원 정보 (Employee 조인)]
    private String pernr;       // 사번
    private String name;       // 성명 

    // [품의 정보 (Req 조인)]
    private String reqId;       // ZREQ_ID
    private String reqTitle;    // 품의 제목

    // [발령 규칙 정보 (Rule 조인)]
    private String ruleId;      // ZRULE_ID
    private String ruleName;    // 발령 기준명 (예: 정기 승진)
    private String ruleType;    // 발령 유형 코드 (예: 20)

    // [상세 변경 항목 리스트 (Detail 조인)]
    private List<AppointDetailDto> details;
}
