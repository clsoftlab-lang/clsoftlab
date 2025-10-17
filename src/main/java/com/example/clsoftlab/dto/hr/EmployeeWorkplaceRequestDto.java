package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWorkplaceRequestDto {

	private Long id;
	
	@NotBlank(message = "사번은 필수입니다.")
	@Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;
	
	@NotBlank(message = "근무지 코드는 필수입니다.")
    @Size(max = 10, message = "근무지 코드는 최대 10자까지 입력 가능합니다.")
    private String workLocCode;

    @NotNull(message = "시작일자는 필수입니다.")
    private LocalDate startDate;

    private LocalDate endDate;

    @NotBlank(message = "현재 근무지 여부는 필수입니다.")
    @Pattern(regexp = "[YN]", message = "현재 근무지 여부는 Y 또는 N만 가능합니다.")
    private String currentYn;

    @Size(max = 255, message = "비고는 최대 255자까지 입력 가능합니다.")
    private String remark;
}
