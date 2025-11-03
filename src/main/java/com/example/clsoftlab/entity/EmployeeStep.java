package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.pay.EmployeeStepRequestDto;

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
@Table(name = "ZHR_EMP_STEP")
public class EmployeeStep extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERNR", nullable = false)
    private EmployeeMaster employee;

    @Column(name = "ZGRADE_CD", nullable = false, length = 20)
    private String gradeCode;

    @Column(name = "ZSTEP_NO", nullable = false)
    private Integer stepNo; 
    
    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate;

    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update(EmployeeStepRequestDto dto) {
    	this.fromDate = dto.getFromDate();
    	this.toDate = dto.getToDate();
    	this.note = dto.getNote();
    }
}
