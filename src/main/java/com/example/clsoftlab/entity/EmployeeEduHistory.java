package com.example.clsoftlab.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.clsoftlab.dto.hr.EmployeeEduHistoryRequestDto;

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
@Table(name = "ZHR_EMP_EDU_HIST")
public class EmployeeEduHistory extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDU_HIST_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @Column(name = "ZSEQ", nullable = false)
    private Integer seq;

    @Column(name = "ZSCHOOL", nullable = false, length = 100)
    private String school;

    @Column(name = "ZCOUNTRY", nullable = false, length = 10)
    private String country;

    @Column(name = "ZSTART_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "ZEND_DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "ZDEGREE", nullable = false, length = 20)
    private String degree;

    @Column(name = "ZMAJOR", nullable = false, length = 100)
    private String major;

    @Column(name = "ZSTATUS", nullable = false, length = 20)
    private String status;

    @Column(name = "ZFINAL_YN", nullable = false, length = 1)
    private String finalYn = "N";

    @Column(name = "ZREMARK", length = 255)
    private String remark;
    
    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version = 0L;
    
    public void update (EmployeeEduHistoryRequestDto dto) {
    	this.seq = dto.getSeq();
    	this.school = dto.getSchool();
    	this.country = dto.getCountry();
    	this.startDate = dto.getStartDate();
    	this.endDate = dto.getEndDate();
    	this.degree = dto.getDegree();
    	this.major = dto.getMajor();
    	this.status = dto.getStatus();
    	this.finalYn = dto.getFinalYn();
    	this.remark = dto.getRemark();
    }
}
