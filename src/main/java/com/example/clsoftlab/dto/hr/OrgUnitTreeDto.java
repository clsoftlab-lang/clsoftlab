package com.example.clsoftlab.dto.hr;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrgUnitTreeDto {

	private String orgCode;
	private String orgName;
	private String useYn;
	private List<OrgUnitTreeDto> children = new ArrayList<>();
	
	public OrgUnitTreeDto(OrgUnitFlatDto flatDto) {
        this.orgCode = flatDto.getOrgCode();
        this.orgName = flatDto.getOrgName();
        this.useYn = flatDto.getUseYn();
    }
}
