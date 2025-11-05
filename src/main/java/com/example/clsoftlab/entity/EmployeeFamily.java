package com.example.clsoftlab.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;

import com.example.clsoftlab.dto.pay.EmployeeFamilyRequestDto;

import jakarta.persistence.Column;
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
@Table(name = "ZHR_EMP_FAMILY")
public class EmployeeFamily extends BaseEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAMILY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZEMP_NO", referencedColumnName = "PERNR", nullable = false)
    private EmployeeMaster employee;

    @Column(name = "ZFAM_SEQ", nullable = false)
    private Integer familySeq;

    @Column(name = "ZFAM_TYPE", nullable = false, length = 20)
    private String familyType;

    @Column(name = "ZFAM_NAME", nullable = false, length = 100)
    private String familyName;

    @Column(name = "ZBIRTH", nullable = false)
    private LocalDate birth;

    @Column(name = "ZGENDER", nullable = false, length = 1)
    private String gender;

    @ColumnDefault("'N'")
    @Column(name = "ZDEPEND_YN", nullable = false, length = 1)
    private String dependYn;

    @ColumnDefault("'N'")
    @Column(name = "ZALLOW_YN", nullable = false, length = 1)
    private String allowYn;

    @ColumnDefault("'N'")
    @Column(name = "ZTAX_YN", nullable = false, length = 1)
    private String taxYn;

    @ColumnDefault("'N'")
    @Column(name = "ZDISABLED_YN", nullable = false, length = 1)
    private String disabledYn;

    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate;

    @ColumnDefault("'9999-12-31'")
    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    @Version
    @Column(name = "VERSION")
    private Long version;

    
    public void update (EmployeeFamilyRequestDto dto) {
    	this.familyType = dto.getFamilyType();
    	this.familyName = dto.getFamilyName();
    	this.birth = dto.getBirth();
    	this.gender = dto.getGender();
    	this.dependYn = dto.getDependYn();
    	this.allowYn = dto.getAllowYn();
    	this.taxYn = dto.getTaxYn();
    	this.disabledYn = dto.getDisabledYn();
    	this.fromDate = dto.getFromDate();
    	this.toDate = dto.getToDate();
    	this.note = dto.getNote();
    }

}
