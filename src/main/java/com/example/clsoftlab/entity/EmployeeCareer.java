package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.EmployeeCareerRequestDto;

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
@Table(name = "ZHR_EMP_CAREER")
public class EmployeeCareer extends BaseEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAREER_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @Column(name = "ZSEQ", nullable = false)
    private Integer seq;

    @Column(name = "ZCAREER_TYPE", nullable = false, length = 1)
    private String careerType;

    @Column(name = "ZCOMP_NAME", nullable = false, length = 100)
    private String compName;

    @Column(name = "ZDEPT", length = 100)
    private String dept;

    @Column(name = "ZPOSITION", nullable = false, length = 100)
    private String position;

    @Column(name = "ZSTART_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "ZEND_DATE")
    private LocalDate endDate;

    @Column(name = "ZPERIOD_MONTH")
    private Integer periodMonth;
    
    @Column(name = "ZRESIGN_REASON", length = 100)
    private String resignReason;

    @Column(name = "ZATTACH_ID", length = 100)
    private String attachId;

    @Column(name = "ZREMARK", length = 200)
    private String remark;
    
    public void update (EmployeeCareerRequestDto dto) {
    	this.seq = dto.getSeq();
    	this.careerType = dto.getCareerType();
    	this.compName = dto.getCompName();
    	this.dept = dto.getDept();
    	this.position = dto.getPosition();
    	this.startDate = dto.getStartDate();
    	this.endDate = dto.getEndDate();
    	this.periodMonth = dto.getPeriodMonth();
    	this.resignReason = dto.getResignReason();
    	this.attachId = dto.getAttachId();
    	this.remark = dto.getRemark();
    }
}
