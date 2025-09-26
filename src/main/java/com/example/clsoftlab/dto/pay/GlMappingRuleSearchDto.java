package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlMappingRuleSearchDto {

	private String itemCode;
	private String bizCode;
	private String costCntr;
	private String useYn;
}
