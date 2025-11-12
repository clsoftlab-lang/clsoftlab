package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BizPlaceDetailDto {

	private Long id;
    private String bizCode;
    private String bizName;
    private String postcode;
    private String sido;
    private String sigungu;
    private String addrMain;
    private String addrDetail;
    private String regNo;
    private String tel;
    private String email;
    private String workHour;
    private String workDays;
    private String type;
    private String managerPernr;
    private String managerName;
    private String useYn;
    private String remark;
}
