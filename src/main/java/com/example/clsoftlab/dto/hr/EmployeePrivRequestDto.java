package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
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
public class EmployeePrivRequestDto {

	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotBlank(message = "성명은 필수 입력 항목입니다.")
    @Size(max = 50, message = "성명은 최대 50자까지 입력 가능합니다.")
    private String name;

    @NotBlank(message = "성별은 필수 입력 항목입니다.")
    @Size(max = 1, message = "성별 코드는 1자여야 합니다.")
    private String gender;

    @NotNull(message = "생년월일은 필수 입력 항목입니다.")
    private LocalDate birthdate;

    @NotBlank(message = "주민등록번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^\\d{6}-\\d{7}$", message = "주민등록번호 형식이 올바르지 않습니다. (******-*******)")
    private String ssn;

    @NotBlank(message = "국적은 필수 입력 항목입니다.")
    @Size(max = 30, message = "국적은 최대 30자까지 입력 가능합니다.")
    private String nationality;

    @NotBlank(message = "연락처는 필수 입력 항목입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "연락처 형식이 올바르지 않습니다. (010-XXXX-XXXX)")
    private String phoneNo;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 100, message = "이메일은 최대 100자까지 입력 가능합니다.")
    private String email;

    @Size(max = 255, message = "주소는 최대 255자까지 입력 가능합니다.")
    private String addr;

    @Size(max = 10, message = "결혼여부는 최대 10자까지 입력 가능합니다.")
    private String maritalStatus;

    @Size(max = 100, message = "병역사항은 최대 100자까지 입력 가능합니다.")
    private String militaryInfo;

    @Size(max = 1, message = "장애여부 코드는 1자여야 합니다.")
    private String disabilityYn;

    @Size(max = 50, message = "비상연락인 이름은 최대 50자까지 입력 가능합니다.")
    private String emergencyName;

    @Size(max = 20, message = "비상연락인 관계는 최대 20자까지 입력 가능합니다.")
    private String emergencyRel;

    @Size(max = 20, message = "비상연락인 연락처는 최대 20자까지 입력 가능합니다.")
    private String emergencyPhone;

}
