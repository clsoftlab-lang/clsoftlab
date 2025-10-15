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
public class EmployeeEduHistoryDetailDto {

	private Long id;
    private String pernr;
    private Integer seq;
    private String school;
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    private String degree;
    private String major;
    private String status;
    private String finalYn;
    private String remark;
}
