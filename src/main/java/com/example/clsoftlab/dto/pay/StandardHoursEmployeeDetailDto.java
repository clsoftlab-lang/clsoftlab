package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardHoursEmployeeDetailDto {

	private long id;
	private String calYm;
	private String employeePernr;
	private String employeeName;
	private Integer standardHours;
	private String reasonCode;
	private String note;
}
