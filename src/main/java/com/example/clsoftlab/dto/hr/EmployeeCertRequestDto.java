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
public class EmployeeCertRequestDto {

	private Long id; // 수정 시 사용할 ID 필드 추가
	
	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotNull(message = "순번은 필수 입력 항목입니다.")
    private Integer seq;

    @NotBlank(message = "자격증명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "자격증명은 최대 100자까지 입력 가능합니다.")
    private String certName;

    @NotBlank(message = "발급기관은 필수 입력 항목입니다.")
    @Size(max = 100, message = "발급기관은 최대 100자까지 입력 가능합니다.")
    private String certOrg;

    @Size(max = 50, message = "자격증번호는 최대 50자까지 입력 가능합니다.")
    private String certNo;

    @NotNull(message = "취득일자는 필수 입력 항목입니다.")
    private LocalDate getDate;

    private LocalDate expDate;

    @Size(max = 1, message = "직무 연계 여부는 최대 1자까지 입력 가능합니다.")
    private String jobRel;

    @Size(max = 200, message = "비고는 최대 200자까지 입력 가능합니다.")
    private String remark;
    
    @Size(max = 32, message = "첨부파일 ID는 최대 32자까지 입력 가능합니다.")
    private String attachId;
}
