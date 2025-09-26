package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 이름, 코드 전달용
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalcOrderSimpleDetailDto {

	private String itemCode;
	private String itemName;
	
 }

