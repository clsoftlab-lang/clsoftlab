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
public class EmployeeCertDetailDto {

	private Long id;
    private String pernr;
    private Integer seq;
    private String certName;
    private String certOrg;
    private String certNo;
    private LocalDate getDate;
    private LocalDate expDate;
    private String jobRel;
    private String remark;
    private String attachId;
}
