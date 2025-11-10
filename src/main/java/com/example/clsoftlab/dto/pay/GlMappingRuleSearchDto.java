package com.example.clsoftlab.dto.pay;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlMappingRuleSearchDto {

	private List<String> itemCode;
	private List<String> bizCode;
	private List<String> costCntr;
	private String useYn;
}
