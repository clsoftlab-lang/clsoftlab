package com.example.clsoftlab.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDetailDto {

	private String id; // 예: ADMIN, USER
    private String roleName;
    private String roleDesc;
    private Integer order;
    private String useYn;
}
