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
public class EvalTemplateDetailDto {

	private String templateId;
    private String templateName;
    private String year;
    private String evType;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
}
