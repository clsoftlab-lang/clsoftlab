package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointReqRequestDto {

	@Size(max = 20, message = "ID는 20자 이하여야 합니다.")
    private String id; // ZREQ_ID

    @NotBlank(message = "품의 제목은 필수입니다.")
    @Size(max = 100, message = "품의 제목은 100자 이하여야 합니다.")
    private String title; // ZTITLE

    @Size(max = 500, message = "품의 내용은 500자 이하여야 합니다.")
    private String desc; // ZDESC

    @NotNull(message = "기안 일자는 필수입니다.")
    private LocalDate reqDate; // ZREQ_DATE

    @NotBlank(message = "기안자 사번은 필수입니다.")
    @Size(max = 20, message = "기안자 사번은 20자 이하여야 합니다.")
    private String reqBy; // ZREQ_BY

    @Size(max = 10, message = "상태 코드는 10자 이하여야 합니다.")
    private String status = "10"; // ZSTATUS (기본값 10: 임시저장)
}
