package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BizPlaceListDto {

	private String bizCode;
	private String bizName;
	private String address;
	private String regNo;
	private String useYn;
	private String managerId;
}
