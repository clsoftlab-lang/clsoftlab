package com.example.clsoftlab.dto.hr;

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
public class BizPlaceRequestDto {
	
    @NotBlank(message = "사업장 코드는 필수 입력 항목입니다.")
    @Size(max = 10, message = "사업장 코드는 최대 10자까지 입력 가능합니다.")
    private String bizCode;

    @NotBlank(message = "사업장명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "사업장명은 최대 100자까지 입력 가능합니다.")
    private String bizName;

    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    @Size(max = 200, message = "주소는 최대 200자까지 입력 가능합니다.")
    private String address;

    @Size(max = 20, message = "사업자등록번호는 최대 20자까지 입력 가능합니다.")
    private String regNo;

    @Size(max = 20, message = "전화번호는 최대 20자까지 입력 가능합니다.")
    private String tel;

    @Size(max = 50, message = "이메일은 최대 50자까지 입력 가능합니다.")
    private String email;

    @Size(max = 20, message = "근무시간은 최대 20자까지 입력 가능합니다.")
    private String workHour;

    @Size(max = 10, message = "주간근무제는 최대 10자까지 입력 가능합니다.")
    private String workDays;

    @Size(max = 20, message = "사업장 유형은 최대 20자까지 입력 가능합니다.")
    private String type;

    @Size(max = 12, message = "담당자 ID는 최대 12자까지 입력 가능합니다.")
    private String managerId;

    @NotBlank(message = "사용 여부는 필수 입력 항목입니다.")
    @Pattern(regexp = "[YN]", message = "사용 여부는 'Y' 또는 'N'만 입력 가능합니다.")
    private String useYn;

    @Size(max = 200, message = "비고는 최대 200자까지 입력 가능합니다.")
    private String remark;
	
}
