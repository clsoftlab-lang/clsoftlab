package com.example.clsoftlab.dto.common;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeDetailDto {

    private Long id; 
    private String groupId;
    private String code;
    private String name;
    private String nameEn;
    private LocalDate startDate;
    private LocalDate endDate;
    private String useYn;
    private Integer order = 0;
    private String remark;
    private String isSystem;
}
