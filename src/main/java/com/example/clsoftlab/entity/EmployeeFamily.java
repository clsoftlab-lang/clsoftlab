package com.example.clsoftlab.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.clsoftlab.converter.CryptoConverter;
import com.example.clsoftlab.dto.hr.EmployeeFamilyRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@DynamicInsert
@DynamicUpdate
@Table(name = "ZHR_EMP_FAMILY")
public class EmployeeFamily extends BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAMILY_ID")
    private Long id; // 가족 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERNR", referencedColumnName = "PERNR", nullable = false)
    private EmployeeMaster employee; // 사원번호 (FK)

    @Column(name = "ZFAM_SEQ", nullable = false)
    private Integer familySeq; // 가족 순번

    @Column(name = "ZFAM_TYPE", nullable = false, length = 10)
    private String familyType; // 관계 코드 (공통코드: HR_REL_TYPE)

    @Column(name = "ZFAM_NAME", nullable = false, length = 100)
    private String familyName; // 가족 성명

    @Convert(converter = CryptoConverter.class)
    @Column(name = "ZSSN", nullable = false, length = 256)
    private String ssn; // 주민등록번호 (암호화 필수)

    @Column(name = "ZBIRTH", nullable = false)
    private LocalDate birth; // 생년월일

    @Column(name = "ZGENDER", nullable = false, length = 1)
    private String gender; // 성별 (M/F)

    @Column(name = "ZJOB_TYPE", length = 10)
    private String jobType; // 직업 구분 (공통코드: HR_JOB_TYPE)

    @Column(name = "ZSCH_TYPE", length = 10)
    private String schoolType; // 학력/학교 구분 (공통코드: HR_SCH_TYPE)

    @Column(name = "ZSCH_NAME", length = 100)
    private String schoolName; // 학교명

    @ColumnDefault("'Y'")
    @Column(name = "ZLIVE_YN", nullable = false, length = 1)
    private String liveYn; // 동거 여부 (Y/N)

    @ColumnDefault("'N'")
    @Column(name = "ZDEPEND_YN", nullable = false, length = 1)
    private String dependYn; // 부양가족 공제 여부 (Y/N)

    @ColumnDefault("'N'")
    @Column(name = "ZALLOW_YN", nullable = false, length = 1)
    private String allowYn; // 가족수당 지급 여부 (Y/N)

    @ColumnDefault("'N'")
    @Column(name = "ZTAX_YN", nullable = false, length = 1)
    private String taxYn; // 의료비/교육비 등 공제 대상 (Y/N)

    @ColumnDefault("'N'")
    @Column(name = "ZDISABLED_YN", nullable = false, length = 1)
    private String disabledYn; // 장애인 여부 (Y/N)

    @Column(name = "ZPHONE", length = 20)
    private String phone; // 연락처

    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate; // 적용 시작일

    @ColumnDefault("'9999-12-31'")
    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate; // 적용 종료일

    @Column(name = "ZNOTE", length = 500)
    private String note; // 비고
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update(EmployeeFamilyRequestDto dto) {
        this.familySeq = dto.getFamilySeq();
        this.familyType = dto.getFamilyType();
        this.familyName = dto.getFamilyName();
        // this.ssn = dto.getSsn(); // [정책] 주민번호 수정 불가
        
        this.birth = dto.getBirth();
        this.gender = dto.getGender();

        // 직업 및 학력 정보
        this.jobType = dto.getJobType();
        this.schoolType = dto.getSchoolType();
        this.schoolName = dto.getSchoolName();

        // 상태 플래그
        this.liveYn = dto.getLiveYn();
        this.dependYn = dto.getDependYn();
        this.allowYn = dto.getAllowYn();
        this.taxYn = dto.getTaxYn();
        this.disabledYn = dto.getDisabledYn();
        
        // 연락처
        this.phone = dto.getPhone();

        // 기간 및 비고
        this.fromDate = dto.getFromDate();
        this.toDate = dto.getToDate();
        this.note = dto.getNote();
    }
}
