package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalcOrderDetailDto {
	
	private Long id;
    private String payItemCode;
    private String payItemName;
    private Integer order;
    private String groupCode;
    private CalcOrderSimpleDetailDto dependsOn;
    private String dependsOnPayItemCode;
    private String note;
    private String useYn;

}
