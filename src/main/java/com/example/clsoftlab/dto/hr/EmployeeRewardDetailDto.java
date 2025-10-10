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
public class EmployeeRewardDetailDto {

	private Long id;
    private String pernr;
    private Integer seq;
    private LocalDate awardDate;
    private String awardName;
    private String awardType;
    private String awardOrg;
    private String reason;
    private String content;
    private String attachId;
    private String remark;
}
