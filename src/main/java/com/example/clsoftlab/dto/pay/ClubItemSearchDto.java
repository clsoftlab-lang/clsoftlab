package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubItemSearchDto {
	private Long id;
	private String clubCode;
	private String clubName;
}
