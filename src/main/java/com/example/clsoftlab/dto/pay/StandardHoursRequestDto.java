package com.example.clsoftlab.dto.pay;

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
public class StandardHoursRequestDto {

	@NotBlank(message = "기준연월은 필수입니다.")
    @Pattern(regexp = "^\\d{6}$", message = "기준연월은 YYYYMM 형식이어야 합니다.")
    private String calYm; // 기준연월 (ZCAL_YM)

    @Size(max = 10, message = "직군코드는 최대 10자까지 입력 가능합니다.")
    private String jobGroup; // 직군코드 (ZJOB_GRP), 생략 가능

    @NotNull(message = "소정 근로시간은 필수입니다.")
    private Integer standardHours; // 해당 월 소정 근로시간 (ZSTD_HOURS)

    @Size(max = 500, message = "설명은 최대 500자까지 입력 가능합니다.")
    private String note; // 설명 (ZNOTE)

}
