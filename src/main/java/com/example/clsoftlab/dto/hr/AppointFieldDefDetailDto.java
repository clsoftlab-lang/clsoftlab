package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointFieldDefDetailDto {

	private Long id; 
    private String ruleType; // ZRULE_TYPE
    private String fieldCode; // ZFIELD_CD
    private String fieldName; // ZFIELD_NM
    private String dataType; // ZDATA_TYPE
    private String required; // ZREQUIRED
    private Integer order; // ZORDER
}
