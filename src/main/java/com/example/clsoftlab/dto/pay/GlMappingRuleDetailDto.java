package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlMappingRuleDetailDto {

	private Long id;
    private String itemCode;
    private String itemName;
    private String bizCode;
    private String costCntr;
    private String glAccount;
    private String note;
    private String useYn;
}
