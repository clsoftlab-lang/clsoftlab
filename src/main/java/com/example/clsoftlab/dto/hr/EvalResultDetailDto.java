package com.example.clsoftlab.dto.hr;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvalResultDetailDto {

    private Long id;
    private String templateId;
    private String templateName;
    private String year;         // ZYEAR -> year
    private String evType;       // ZEV_TYPE -> evType
    private String evalStep;     // seq -> evalStep
    private String targetEmpId;  // pernr -> targetEmpId
    private String targetEmpName;
    private String evalEmpId;    // reviewer -> evalEmpId
    private String evalEmpName;
    private Integer totalScore;
    private String evalGrade;    // grade -> evalGrade
    private String comment;
    private String status;
    private LocalDateTime submitDate;
}
