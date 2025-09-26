package com.example.clsoftlab.dto.pay;

import jakarta.validation.constraints.NotBlank;
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
public class AllowCycleRequestDto {

	@NotBlank(message = "항목 코드는 필수입니다.")
    @Size(max = 50, message = "항목 코드는 최대 50자까지 입력 가능합니다.")
    private String itemCode;

    @NotBlank(message = "지급 주기는 필수입니다.")
    @Pattern(regexp = "MONTHLY|QUARTER|HALF|YEAR", message = "지급 주기는 MONTHLY, QUARTER, HALF, YEAR 중 하나여야 합니다.")
    private String cycle;

    @Size(max = 500, message = "설명은 최대 500자까지 입력 가능합니다.")
    private String note;

    @NotBlank(message = "사용 여부는 필수입니다.")
    @Pattern(regexp = "[YN]", message = "사용 여부는 'Y' 또는 'N' 이어야 합니다.")
    private String useYn;
}
