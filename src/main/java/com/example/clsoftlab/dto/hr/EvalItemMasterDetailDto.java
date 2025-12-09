package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvalItemMasterDetailDto {

	private Long id;
    private String templateId;
    private String itemName;
    private String itemDesc;
    private Integer weight;
    private String useYn = "Y";
    private Integer order;
}
