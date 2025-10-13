package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.EmployeeEduRequestDto;

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
@Table(name = "ZHR_EMP_EDU")
public class EmployeeEdu extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDU_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @Column(name = "ZSEQ", nullable = false)
    private Integer seq;

    @Column(name = "ZEDU_NAME", nullable = false, length = 100)
    private String eduName;

    @Column(name = "ZEDU_TYPE", nullable = false, length = 1)
    private String eduType;

    @Column(name = "ZEDU_ORG", nullable = false, length = 100)
    private String eduOrg;

    @Column(name = "ZSTART_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "ZEND_DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "ZCOMPLETE_YN", nullable = false, length = 1)
    private String completeYn;

    @Column(name = "ZHOUR")
    private Integer hour;
    
    @Column(name = "ZCOST", precision = 13, scale = 2)
    private BigDecimal cost;

    @Column(name = "ZATTACH_ID", length = 100)
    private String attachId;

    @Column(name = "ZREMARK", length = 200)
    private String remark;
    
    public void update (EmployeeEduRequestDto dto) {
    	this.seq = dto.getSeq();
    	this.eduName = dto.getEduName();
    	this.eduType = dto.getEduType();
    	this.eduOrg = dto.getEduOrg();
    	this.startDate = dto.getStartDate();
    	this.endDate = dto.getEndDate();
    	this.completeYn = dto.getCompleteYn();
    	this.hour = dto.getHour();
    	this.cost = dto.getCost();
    	this.attachId = dto.getAttachId();
    	this.remark = dto.getRemark();
    }
}
