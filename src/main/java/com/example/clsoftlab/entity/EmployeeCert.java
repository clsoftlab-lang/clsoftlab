package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.clsoftlab.dto.hr.EmployeeCertRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "ZHR_EMP_CERT")
public class EmployeeCert extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CERT_ID")
    private Long id; // PK

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr; // 사번 (FK)

    @Column(name = "ZSEQ", nullable = false)
    private Integer seq; // 순번

    @Column(name = "ZCERT_CD", length = 20)
    private String certCode;

    @Column(name = "ZCERT_NAME", nullable = false, length = 100)
    private String certName; // 자격증명 (기타일 경우 직접 입력)

    @Column(name = "ZCERT_RANK", length = 20)
    private String certRank;

    @Column(name = "ZCERT_SCORE", precision = 5, scale = 2)
    private BigDecimal certScore;

    @Column(name = "ZCERT_ORG", nullable = false, length = 100)
    private String certOrg; // 발급기관

    @Column(name = "ZCERT_NO", length = 50)
    private String certNo; // 자격증 번호

    @Column(name = "ZGET_DATE", nullable = false)
    private LocalDate getDate; // 취득일

    @Column(name = "ZEXP_DATE")
    private LocalDate expDate; // 만료일

    @ColumnDefault("'N'")
    @Column(name = "ZJOB_REL", length = 1)
    private String jobRel; // 직무 연관성 (Y/N)

    @ColumnDefault("'N'")
    @Column(name = "ZALLOW_YN", nullable = false, length = 1)
    private String allowYn;

    @ColumnDefault("'N'")
    @Column(name = "ZCONFIRM_YN", nullable = false, length = 1)
    private String confirmYn;

    @Column(name = "ZREMARK", length = 200)
    private String remark; // 비고

    @Column(name = "ZATTACH_ID", length = 100)
    private String attachId; // 첨부파일 ID
    
    public void update (EmployeeCertRequestDto dto) {
    	this.seq = dto.getSeq();
    	this.certName = dto.getCertName();
    	this.certOrg = dto.getCertOrg();
    	this.certNo = dto.getCertNo();
    	this.getDate = dto.getGetDate();
    	this.expDate = dto.getExpDate();
    	this.jobRel = dto.getJobRel();
    	this.remark = dto.getRemark();
    	this.attachId = dto.getAttachId();
    }
}
