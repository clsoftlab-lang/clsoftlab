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
public class EmployeeRewardRequestDto {

	private Long id;
	
	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotNull(message = "포상 순번은 필수 입력 항목입니다.")
    private Integer seq;

    @NotNull(message = "수여일자는 필수 입력 항목입니다.")
    private LocalDate awardDate;

    @NotBlank(message = "포상명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "포상명은 최대 100자까지 입력 가능합니다.")
    private String awardName;

    @NotBlank(message = "포상 종류는 필수 입력 항목입니다.")
    @Size(min = 1, max = 1, message = "포상 종류는 한 글자여야 합니다. (I 또는 E)")
    private String awardType;

    @NotBlank(message = "수여기관은 필수 입력 항목입니다.")
    @Size(max = 100, message = "수여기관은 최대 100자까지 입력 가능합니다.")
    private String awardOrg;

    @NotBlank(message = "포상사유는 필수 입력 항목입니다.")
    @Size(max = 200, message = "포상사유는 최대 200자까지 입력 가능합니다.")
    private String reason;

    @Size(max = 100, message = "포상내용은 최대 100자까지 입력 가능합니다.")
    private String content;

    @Size(max = 100, message = "첨부파일 ID는 최대 100자까지 입력 가능합니다.")
    private String attachId;

    @Size(max = 200, message = "비고는 최대 200자까지 입력 가능합니다.")
    private String remark;
}
