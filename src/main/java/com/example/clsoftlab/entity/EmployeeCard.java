package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.converter.CryptoConverter;
import com.example.clsoftlab.dto.hr.EmployeeCardRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ZHR_EMP_CARD")
public class EmployeeCard extends BaseEntity {

	@Id
    @Column(name = "PERNR", length = 10, nullable = false)
    private String pernr; // 사번 (PK)

    // 1. 기본 정보
    @Column(name = "ENAME", length = 50, nullable = false)
    private String name; // 성명

    @Column(name = "ZBIRTH", nullable = false)
    private LocalDate birth; // 생년월일

    @Column(name = "ZSEX", length = 1, nullable = false)
    private String sex; // 성별 (M/F)

    @Column(name = "ZNATION", length = 50, nullable = false)
    private String nation; // 국적

    @Convert(converter = CryptoConverter.class)
    @Column(name = "ZRRN", length = 255, nullable = false)
    private String rrn; // 주민등록번호 (암호화)

    @Column(name = "ZPHONE", length = 20, nullable = false)
    private String phone; // 휴대전화

    @Column(name = "ZEMAIL", length = 100, nullable = false)
    private String email; // 이메일

    @Column(name = "ZADDR", length = 255)
    private String addr; // 주소 및 상세주소

    @Column(name = "ZEMER_PHONE", length = 20)
    private String emerPhone; // 비상연락처

    // 2. 입사 정보 & 상단 공통
    @Column(name = "ZJOIN_DATE", nullable = false)
    private LocalDate joinDate; // 입사일

    @Column(name = "ZFIRST_JOIN_DATE")
    private LocalDate firstJoinDate; // 최초입사일

    @Column(name = "ZJOIN_TYPE", length = 3, nullable = false)
    private String joinType; // 입사유형 (코드)

    @Column(name = "ZJOIN_PATH", length = 3)
    private String joinPath; // 입사경로 (코드)

    @Column(name = "ZCONTRACT_TYPE", length = 3, nullable = false)
    private String contractType; // 고용형태/계약구분 (코드)

    @Column(name = "ZPROBATION_PERIOD", length = 50)
    private String probationPeriod; // 수습기간

    @Column(name = "ZJOIN_CATEGORY", length = 3)
    private String joinCategory; // 입사형태 (코드)

    @Column(name = "ZWORK_STATUS", length = 3, nullable = false)
    private String workStatus; // 재직상태 (코드)

    // 3. 보호자 정보
    @Column(name = "ZPROTECT_NAME", length = 50, nullable = false)
    private String protectName; // 보호자 성명

    @Column(name = "ZPROTECT_REL", length = 20, nullable = false)
    private String protectRel; // 보호자 관계

    @Column(name = "ZPROTECT_PHONE", length = 20, nullable = false)
    private String protectPhone; // 보호자 연락처

    @Column(name = "ZPROTECT_ADDR", length = 255)
    private String protectAddr; // 보호자 주소

    // 4. 병역/보훈 정보
    @Column(name = "ZMIL_SERVICE_STATUS", length = 3)
    private String milServiceStatus; // 병역구분 (코드)

    @Column(name = "ZMIL_TYPE", length = 20)
    private String milType; // 병역종류

    @Column(name = "ZMIL_PERIOD_START")
    private LocalDate milPeriodStart; // 병역 시작일

    @Column(name = "ZMIL_PERIOD_END")
    private LocalDate milPeriodEnd; // 병역 종료일

    @Column(name = "ZVET_YN", length = 1)
    private String vetYn; // 보훈대상여부 (Y/N)

    @Column(name = "ZDISABILITY_YN", length = 1)
    private String disabilityYn; // 장애등록여부 (Y/N)
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update(EmployeeCardRequestDto dto) {
        // 1. 기본 정보
        this.name = dto.getName();
        this.birth = dto.getBirth();
        this.sex = dto.getSex();
        this.nation = dto.getNation();
        this.phone = dto.getPhone();
        this.email = dto.getEmail();
        this.addr = dto.getAddr();
        this.emerPhone = dto.getEmerPhone();

        // 2. 입사 정보 & 상단 공통
        this.joinDate = dto.getJoinDate();
        this.firstJoinDate = dto.getFirstJoinDate();
        this.joinType = dto.getJoinType();
        this.joinPath = dto.getJoinPath();
        this.contractType = dto.getContractType();
        this.probationPeriod = dto.getProbationPeriod();
        this.joinCategory = dto.getJoinCategory();
        this.workStatus = dto.getWorkStatus();

        // 3. 보호자 정보
        this.protectName = dto.getProtectName();
        this.protectRel = dto.getProtectRel();
        this.protectPhone = dto.getProtectPhone();
        this.protectAddr = dto.getProtectAddr();

        // 4. 병역/보훈 정보
        this.milServiceStatus = dto.getMilServiceStatus();
        this.milType = dto.getMilType();
        this.milPeriodStart = dto.getMilPeriodStart();
        this.milPeriodEnd = dto.getMilPeriodEnd();
        this.vetYn = dto.getVetYn();
        this.disabilityYn = dto.getDisabilityYn();
    }
}
