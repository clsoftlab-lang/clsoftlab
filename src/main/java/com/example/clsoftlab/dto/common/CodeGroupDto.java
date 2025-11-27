package com.example.clsoftlab.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeGroupDto {

	private String id;
    private String groupName;
    private String useYn = "Y";
    private Integer order = 0;
    private String remark;
    private String isSystem = "N";
}
