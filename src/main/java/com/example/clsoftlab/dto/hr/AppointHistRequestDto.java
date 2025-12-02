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
public class AppointHistRequestDto {

	@Size(max = 20, message = "ID는 20자 이하여야 합니다.")
    private String id; // ZHIST_ID

    @NotBlank(message = "발령 품의 ID는 필수입니다.")
    @Size(max = 20, message = "발령 품의 ID는 20자 이하여야 합니다.")
    private String reqId; // ZREQ_ID (부모 ID)

    @NotBlank(message = "대상자 사번은 필수입니다.")
    @Size(max = 20, message = "대상자 사번은 20자 이하여야 합니다.")
    private String pernr; // PERNR

    @NotBlank(message = "발령 기준 ID는 필수입니다.")
    @Size(max = 10, message = "발령 기준 ID는 10자 이하여야 합니다.")
    private String ruleId; // ZRULE_ID

    @NotNull(message = "발령 시행일은 필수입니다.")
    private LocalDate effDate; // ZEFF_DATE

    @Size(max = 300, message = "비고는 300자 이하여야 합니다.")
    private String remark; // ZREMARK

    @Pattern(regexp = "^[YN]$", message = "취소 여부는 Y 또는 N이어야 합니다.")
    private String isCanceled = "N"; // IS_CANCELED
}
