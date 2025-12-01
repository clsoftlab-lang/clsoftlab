package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointRuleDetailDto {

	private String id; // ZRULE_ID
    private String ruleName; // ZRULE_NM
    private String ruleType; // ZRULE_TYPE
    private String condDesc; // ZCOND_DESC
    private String useYn;
}
