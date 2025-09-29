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
public class OrgUnitRequestDto {

	@NotBlank(message = "조직 코드는 필수입니다.")
	@Size(max = 20, message = "조직 코드는 최대 20자까지 입력 가능합니다.")
	private String orgCode;
	
	@Size(max = 20, message = "상위 조직 코드는 최대 20자까지 입력 가능합니다.")
	private String parentOrgCode;
	
	@NotBlank(message = "조직명은 필수입니다.")
	@Size(max = 100, message = "조직명은 최대 100자까지 입력 가능합니다.")
	private String orgName;
	
	@NotBlank(message = "조직 유형은 필수입니다.")
	@Size(max = 10, message = "조직 유형은 최대 10자까지 입력 가능합니다.")
	private String orgType;
	
	@NotBlank(message = "사업장 코드는 필수입니다.")
	@Size(max = 10, message = "사업장 코드는 최대 10자까지 입력 가능합니다.")
	private String bizCode;
	
	@NotNull(message = "유효 시작일은 필수입니다.")
	private LocalDate validFrom;
	
	private LocalDate validTo;
	
	@Size(max = 20, message = "대표 직책은 최대 20자까지 입력 가능합니다.")
	private String mainPos;
	
	@Size(max = 12, message = "조직 담당자 ID는 최대 12자까지 입력 가능합니다.")
	private String managerId;
	
	@NotBlank(message = "사용 여부는 필수입니다.")
	@Size(max = 1, message = "사용 여부는 'Y' 또는 'N' 중 하나여야 합니다.")
	private String useYn;
	
	@Size(max = 200, message = "비고는 최대 200자까지 입력 가능합니다.")
	private String remark;
}
