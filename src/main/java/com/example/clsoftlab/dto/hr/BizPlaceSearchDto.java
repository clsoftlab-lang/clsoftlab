package com.example.clsoftlab.dto.hr;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BizPlaceSearchDto {

	private List<String> bizCode;
	private String sido;
	private String sigungu;
	private String useYn;
}
