package com.example.clsoftlab.dto.pay;

import org.hibernate.validator.constraints.Range;

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
public class PayCycleRequestDto {

	@NotBlank(message = "직군 코드는 필수값입니다.")
    @Size(max = 20, message = "직군 코드는 최대 20자까지 입력 가능합니다.")
    private String jobGroup;

    @NotNull(message = "기준 시작일은 필수값입니다.")
    @Range(min = 1, max = 31, message = "기준 시작일은 1~31 사이의 값이어야 합니다.")
    private int startDay;

    @NotNull(message = "기준 종료일은 필수값입니다.")
    @Range(min = 1, max = 31, message = "기준 종료일은 1~31 사이의 값이어야 합니다.")
    private int endDay;

    @NotNull(message = "실지급일은 필수값입니다.")
    @Range(min = 1, max = 31, message = "실지급일은 1~31 사이의 값이어야 합니다.")
    private int payDay;

    @NotBlank(message = "사용 여부는 필수값입니다.")
    @Pattern(regexp = "Y|N", message = "사용 여부는 'Y' 또는 'N'만 가능합니다.")
    private String useYn;

    @Size(max = 500, message = "비고는 최대 500자까지 입력 가능합니다.")
    private String note;
}
