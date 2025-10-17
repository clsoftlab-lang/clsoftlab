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
public class EmployeeWorkplaceDetailDto {

	private Long id;
    private String pernr;
    private String bizPlaceBizCode;
    private String bizPlaceBizName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String currentYn;
    private String remark;
}
