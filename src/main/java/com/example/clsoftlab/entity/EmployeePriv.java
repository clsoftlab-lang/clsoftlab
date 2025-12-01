package com.example.clsoftlab.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.clsoftlab.converter.CryptoConverter;
import com.example.clsoftlab.dto.hr.EmployeePrivRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
@Table(name = "ZHR_EMP_PRIV")
public class EmployeePriv extends BaseEntity {

	@Id
    @Column(name = "PERNR", length = 10)
    private String pernr;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId 
    @JoinColumn(name = "PERNR")
    private EmployeeMaster employee;

    @Column(name = "GENDER", nullable = false, length = 1)
    private String gender;

    @Column(name = "BIRTHDATE", nullable = false)
    private LocalDate birthDate;

    @Convert(converter = CryptoConverter.class)
    @Column(name = "SSN", nullable = false, length = 255)
    private String ssn;

    @Builder.Default
    @Column(name = "NATION_CD", nullable = false, length = 20)
    @ColumnDefault("'KR'")
    private String nationCode = "KR";

    @Column(name = "PHONE_NO", nullable = false, length = 20)
    private String phoneNo;

    @Column(name = "HOME_TEL", length = 20)
    private String homeTel;

    @Column(name = "EMAIL", length = 100)
    private String email;

    // --- 주소 (카카오 API) ---
    @Column(name = "ZPOSTCODE", length = 5)
    private String postCode;

    @Column(name = "ZSIDO", length = 50)
    private String sido;

    @Column(name = "ZSIGUNGU", length = 50)
    private String sigungu;

    @Column(name = "ZADDR_MAIN", nullable = false, length = 150)
    private String addrMain;

    @Column(name = "ZADDR_DETAIL", length = 100)
    private String addrDetail;
    // ----------------------

    // [결혼 상태 코드] Default '10' (미혼)
    @Builder.Default
    @Column(name = "MARITAL_CD", length = 20)
    @ColumnDefault("'10'")
    private String maritalCode = "10";

    // [병역 구분 코드] Default '00' (비대상)
    @Builder.Default
    @Column(name = "MILITARY_CD", length = 20)
    @ColumnDefault("'00'")
    private String militaryCode = "00";

    // [장애 여부] Default 'N'
    @Builder.Default
    @Column(name = "DISABILITY_YN", length = 1)
    @ColumnDefault("'N'")
    private String disabilityYn = "N";

    @Column(name = "EMERGENCY_NAME", length = 50)
    private String emergencyName;

    @Column(name = "EMERGENCY_REL", length = 20)
    private String emergencyRel;

    @Column(name = "EMERGENCY_PHONE", length = 20)
    private String emergencyPhone;
    
    
    public void update(EmployeePrivRequestDto dto) {
        this.gender = dto.getGender();
        this.birthDate = dto.getBirthDate();
        this.nationCode = dto.getNationCode();
        this.phoneNo = dto.getPhoneNo();
        this.homeTel = dto.getHomeTel();
        this.email = dto.getEmail();
        
        // 주소 관련
        this.postCode = dto.getPostCode(); 
        this.sido = dto.getSido();
        this.sigungu = dto.getSigungu();
        this.addrMain = dto.getAddrMain();
        this.addrDetail = dto.getAddrDetail();
        
        this.maritalCode = dto.getMaritalCode();
        this.militaryCode = dto.getMilitaryCode();
        this.disabilityYn = dto.getDisabilityYn();
        
        this.emergencyName = dto.getEmergencyName();
        this.emergencyRel = dto.getEmergencyRel();
        this.emergencyPhone = dto.getEmergencyPhone();
    }
}
