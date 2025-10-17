package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
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
public class EmployeeCardRequestDto {

	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr; // 사번 (PK)

    // 1. 기본 정보
    @NotBlank(message = "성명은 필수 입력 항목입니다.")
    @Size(max = 50, message = "성명은 최대 50자까지 입력 가능합니다.")
    private String name; // 성명

    @NotNull(message = "생년월일은 필수 입력 항목입니다.")
    private LocalDate birth; // 생년월일

    @NotBlank(message = "성별은 필수 입력 항목입니다.")
    @Size(max = 1, message = "성별 코드는 1자만 입력 가능합니다.")
    private String sex; // 성별 (M/F)

    @NotBlank(message = "국적은 필수 입력 항목입니다.")
    @Size(max = 50, message = "국적은 최대 50자까지 입력 가능합니다.")
    private String nation; // 국적
    private String rrn; // 주민등록번호 (암호화 전 평문)

    @NotBlank(message = "휴대전화는 필수 입력 항목입니다.")
    private String phone; // 휴대전화

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email; // 이메일
    private String addr; // 주소 및 상세주소
    private String emerPhone; // 비상연락처

    // 2. 입사 정보 & 상단 공통
    @NotNull(message = "입사일은 필수 입력 항목입니다.")
    private LocalDate joinDate; // 입사일
    private LocalDate firstJoinDate; // 최초입사일

    @NotBlank(message = "입사유형은 필수 입력 항목입니다.")
    private String joinType; // 입사유형 (코드)
    private String joinPath; // 입사경로 (코드)

    @NotBlank(message = "고용형태는 필수 입력 항목입니다.")
    private String contractType; // 고용형태/계약구분 (코드)
    private String probationPeriod; // 수습기간
    private String joinCategory; // 입사형태 (코드)

    @NotBlank(message = "재직상태는 필수 입력 항목입니다.")
    private String workStatus; // 재직상태 (코드)

    // 3. 보호자 정보
    @NotBlank(message = "보호자 성명은 필수 입력 항목입니다.")
    private String protectName; // 보호자 성명

    @NotBlank(message = "보호자 관계는 필수 입력 항목입니다.")
    private String protectRel; // 보호자 관계

    @NotBlank(message = "보호자 연락처는 필수 입력 항목입니다.")
    private String protectPhone; // 보호자 연락처
    private String protectAddr; // 보호자 주소

    // 4. 병역/보훈 정보
    private String milServiceStatus; // 병역구분 (코드)
    private String milType; // 병역종류
    private LocalDate milPeriodStart; // 병역 시작일
    private LocalDate milPeriodEnd; // 병역 종료일
    private String vetYn; // 보훈대상여부 (Y/N)
    private String disabilityYn; // 장애등록여부 (Y/N)
}
