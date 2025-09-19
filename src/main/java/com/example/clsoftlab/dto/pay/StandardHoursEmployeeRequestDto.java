package com.example.clsoftlab.dto.pay;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardHoursEmployeeRequestDto {

	 	@NotBlank(message = "기준연월은 필수입니다.")
	    @Pattern(regexp = "^\\d{6}$", message = "기준연월은 YYYYMM 형식이어야 합니다.")
	 	private String calYm;

	    @NotBlank(message = "사번은 필수입니다.")
	    private String empNo;

	    @NotNull(message = "소정 근로시간은 필수입니다.")
	    @PositiveOrZero(message = "소정 근로시간은 0 이상이어야 합니다.")
	    private Integer standardHours;

	    private String reasonCode;
	    private String note;
}
