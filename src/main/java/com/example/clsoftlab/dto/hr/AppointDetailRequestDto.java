package com.example.clsoftlab.dto.hr;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointDetailRequestDto {

	private Long id; // DETAIL_ID

    @NotBlank(message = "발령 이력 ID는 필수입니다.")
    @Size(max = 20, message = "발령 이력 ID는 20자 이하여야 합니다.")
    private String histId; // ZHIST_ID (부모 ID)

    @NotBlank(message = "항목 코드는 필수입니다.")
    @Size(max = 20, message = "항목 코드는 20자 이하여야 합니다.")
    private String fieldCode; // ZFIELD_CD

    @Size(max = 255, message = "변경 전 값은 255자 이하여야 합니다.")
    private String valueOld; // ZVALUE_OLD

    @Size(max = 255, message = "변경 후 값은 255자 이하여야 합니다.")
    private String value; // ZVALUE
}
