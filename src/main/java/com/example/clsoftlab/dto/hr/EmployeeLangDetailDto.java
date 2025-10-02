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
public class EmployeeLangDetailDto {

	private Long id;
    private String pernr;
    private Integer seq;
    private String lang;
    private String examName;
    private String score;
    private LocalDate getDate;
    private LocalDate expDate;
    private String speakLvl;
    private String readLvl;
    private String attachId;
    private String remark;
}
