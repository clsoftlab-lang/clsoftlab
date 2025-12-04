package com.example.clsoftlab.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.clsoftlab.dto.hr.EmployeeLangRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@DynamicInsert
@DynamicUpdate
@Table(name = "ZHR_EMP_LANG")
public class EmployeeLang extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LANG_ID")
    private Long id; // PK

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr; // 사번 (FK)

    @Column(name = "ZSEQ", nullable = false)
    private Integer seq; // 순번

    @Column(name = "ZLANG_CD", nullable = false, length = 20)
    private String langCode;

    @Column(name = "ZTEST_CD", length = 20)
    private String testCode;

    @Column(name = "ZTEST_NAME", length = 50)
    private String testName;

    @Column(name = "ZSCORE", length = 20)
    private String score;

    @Column(name = "ZGET_DATE", nullable = false)
    private LocalDate getDate; // 취득일자

    @Column(name = "ZEXP_DATE")
    private LocalDate expDate; // 만료일자

    @Column(name = "ZSPEAK_LVL", length = 10)
    private String speakLvl; // 회화 수준 (HR_LANG_LVL)

    @Column(name = "ZREAD_LVL", length = 10)
    private String readLvl; // 독해 수준 (HR_LANG_LVL)

    @Column(name = "ZATTACH_ID", length = 100)
    private String attachId; // 첨부파일 ID

    @Column(name = "ZREMARK", length = 200)
    private String remark; // 비고

    @Version
    @Column(name = "VERSION")
    private Long version; // 낙관적 락
    
    public void update(EmployeeLangRequestDto dto) {
        this.seq = dto.getSeq();
        this.langCode = dto.getLangCode();
        this.testCode = dto.getTestCode();
        this.testName = dto.getTestName();
        this.score = dto.getScore();
        this.getDate = dto.getGetDate();
        this.expDate = dto.getExpDate();
        this.speakLvl = dto.getSpeakLvl();
        this.readLvl = dto.getReadLvl();
        this.attachId = dto.getAttachId();
        this.remark = dto.getRemark();
    }
}
