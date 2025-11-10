package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllowCycleDetailDto {

	private Long id;
    private String itemCode;
    private String itemName;
    private String cycle;
    private String note;
    private String useYn;
}
