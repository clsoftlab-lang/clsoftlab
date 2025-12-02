package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointDetailDto {

	private Long id; // DETAIL_ID
    private String histId; // ZHIST_ID (부모 ID)
    private String fieldCode; // ZFIELD_CD
    private String fieldName; // 항목 명칭
    private String valueOld; // ZVALUE_OLD
    private String value; // ZVALUE
}
