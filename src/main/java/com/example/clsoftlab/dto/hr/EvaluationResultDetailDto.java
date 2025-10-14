package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResultDetailDto {

	private Long id;
    private String pernr;
    private String year;
    private String seq;
    private String evType;
    private Integer totalScore;
    private String grade;
    private String reviewer;
    private String comment;
}
