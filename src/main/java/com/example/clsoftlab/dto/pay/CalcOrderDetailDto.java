package com.example.clsoftlab.dto.pay;

import com.example.clsoftlab.entity.CalcOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalcOrderDetailDto {
	
    private String itemCode;
    private String itemName;
    private Integer order;
    private String groupCode;
    private CalcOrder dependsOn;
    private String note;
    private String useYn;

}
