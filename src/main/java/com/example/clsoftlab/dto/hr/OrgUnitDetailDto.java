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
public class OrgUnitDetailDto {

	private String orgCode;
	private String parentOrgCode;
	private String orgName;
	private String orgType;
	private String bizCode;
	private LocalDate validFrom;
	private LocalDate validTo;
	private String mainPos;
	private String managerId;
	private String useYn;
	private String remark;
	
}
