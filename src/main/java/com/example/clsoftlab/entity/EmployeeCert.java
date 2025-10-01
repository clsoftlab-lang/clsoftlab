package com.example.clsoftlab.entity;

import java.time.LocalDate;

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
@Table(name = "ZHR_EMP_CERT")
public class EmployeeCert extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CERT_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @Column(name = "ZSEQ", nullable = false)
    private Integer seq;

    @Column(name = "ZCERT_NAME", nullable = false, length = 100)
    private String certName;

    @Column(name = "ZCERT_ORG", nullable = false, length = 100)
    private String certOrg;

    @Column(name = "ZCERT_NO", length = 50)
    private String certNo;

    @Column(name = "ZGET_DATE", nullable = false)
    private LocalDate getDate;

    @Column(name = "ZEXP_DATE")
    private LocalDate expDate;

    @Column(name = "ZJOB_REL", length = 1)
    private String jobRel;

    @Column(name = "ZREMARK", length = 200)
    private String remark;

    @Column(name = "ZATTACH_ID", length = 32)
    private String attachId;
    
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
