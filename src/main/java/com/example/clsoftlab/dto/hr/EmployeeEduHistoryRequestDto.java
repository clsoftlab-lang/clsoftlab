package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEduHistoryRequestDto {

	private Long id;
	
	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotNull(message = "순번은 필수 입력 항목입니다.")
    @Positive(message = "순번은 양수여야 합니다.")
    private Integer seq;

    @NotBlank(message = "학교명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "학교명은 최대 100자까지 입력 가능합니다.")
    private String school;

    @NotBlank(message = "국가는 필수 입력 항목입니다.")
    @Size(max = 10, message = "국가 코드는 최대 10자까지 입력 가능합니다.")
    private String country;

    @NotNull(message = "입학일은 필수 입력 항목입니다.")
    private LocalDate startDate;

    @NotNull(message = "졸업일은 필수 입력 항목입니다.")
    private LocalDate endDate;

    @NotBlank(message = "학위는 필수 입력 항목입니다.")
    @Size(max = 20, message = "학위는 최대 20자까지 입력 가능합니다.")
    private String degree;

    @NotBlank(message = "전공은 필수 입력 항목입니다.")
    @Size(max = 100, message = "전공은 최대 100자까지 입력 가능합니다.")
    private String major;

    @NotBlank(message = "졸업구분은 필수 입력 항목입니다.")
    @Size(max = 20, message = "졸업구분은 최대 20자까지 입력 가능합니다.")
    private String status;

    @NotBlank(message = "최종학력 여부는 필수 입력 항목입니다.")
    @Pattern(regexp = "[YN]", message = "최종학력 여부는 'Y' 또는 'N'만 가능합니다.")
    private String finalYn;

    @Size(max = 255, message = "비고는 최대 255자까지 입력 가능합니다.")
    private String remark;
}
