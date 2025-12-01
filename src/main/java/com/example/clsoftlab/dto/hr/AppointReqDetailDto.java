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
public class AppointReqDetailDto {

	private String id; // ZREQ_ID
    private String title; // ZTITLE
    private String desc; // ZDESC
    private LocalDate reqDate; // ZREQ_DATE
    private String reqBy; // ZREQ_BY
    private String status; // ZSTATUS (기본값 10: 임시저장)
}
