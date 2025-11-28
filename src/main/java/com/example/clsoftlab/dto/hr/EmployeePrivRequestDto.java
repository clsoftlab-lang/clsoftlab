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
public class EmployeePrivRequestDto {

	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotBlank(message = "성별은 필수 입력 항목입니다.")
    @Size(max = 1, message = "성별 코드는 1자여야 합니다.")
    private String gender;

    @NotNull(message = "생년월일은 필수 입력 항목입니다.")
    private LocalDate birthDate; 

    @NotBlank(message = "주민등록번호는 필수 입력 항목입니다.")
    private String ssn;

    @Size(max = 20, message = "국적 코드는 최대 20자까지 입력 가능합니다.")
    private String nationCode;

    @NotBlank(message = "휴대전화번호는 필수 입력 항목입니다.")
    @Size(max = 20, message = "휴대전화번호는 최대 20자까지 입력 가능합니다.")
    private String phoneNo;

    @Size(max = 20, message = "자택전화번호는 최대 20자까지 입력 가능합니다.")
    private String homeTel;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 100, message = "이메일은 최대 100자까지 입력 가능합니다.")
    private String email;

    // --- 주소 (카카오 API) ---
    @Size(max = 5, message = "우편번호는 최대 5자입니다.")
    private String zipCode; // ZPOSTCODE

    @Size(max = 50, message = "시/도는 최대 50자입니다.")
    private String sido;    // ZSIDO

    @Size(max = 50, message = "시/군/구는 최대 50자입니다.")
    private String sigungu; // ZSIGUNGU

    @NotBlank(message = "기본 주소는 필수 입력 항목입니다.")
    @Size(max = 150, message = "기본 주소는 최대 150자까지 입력 가능합니다.")
    private String addrMain; // ZADDR_MAIN
    
    @Size(max = 100, message = "상세 주소는 최대 100자까지 입력 가능합니다.")
    private String addrDetail; // ZADDR_DETAIL
    // -----------------------

    @Size(max = 20, message = "결혼여부 코드는 최대 20자까지 입력 가능합니다.")
    private String maritalCode;

    @Size(max = 20, message = "병역구분 코드는 최대 20자까지 입력 가능합니다.")
    private String militaryCode;

    @Size(max = 1, message = "장애여부 코드는 1자여야 합니다.")
    private String disabilityYn;

    @Size(max = 50, message = "비상연락인 이름은 최대 50자까지 입력 가능합니다.")
    private String emergencyName;

    @Size(max = 20, message = "비상연락인 관계는 최대 20자까지 입력 가능합니다.")
    private String emergencyRel;

    @Size(max = 20, message = "비상연락인 연락처는 최대 20자까지 입력 가능합니다.")
    private String emergencyPhone;

}
