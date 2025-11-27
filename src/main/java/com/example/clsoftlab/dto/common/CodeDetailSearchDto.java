package com.example.clsoftlab.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeDetailSearchDto {

	private Long id;
	private String code;
    private String name;
    private String nameEn;
}
