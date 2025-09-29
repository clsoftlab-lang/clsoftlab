package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrgUnitFlatDto {
	 	private String orgCode;
	    private String orgName;
	    private String parentOrgCode; 
	    private String useYn;
	    
}
