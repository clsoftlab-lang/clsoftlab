package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDetailDto {

	private Long id;
    private String itemCode;
    private String itemName;
    private Integer point;
    private String comment;
}
