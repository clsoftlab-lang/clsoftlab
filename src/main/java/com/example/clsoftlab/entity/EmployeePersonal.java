package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.EmployeePersonalRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "ZHR_EMP_PERSONAL")
public class EmployeePersonal extends BaseEntity {

	@Id
    @Column(name = "PERNR", length = 10, nullable = false)
    private String pernr;

    @Column(name = "ZJOIN_DATE", nullable = false)
    private LocalDate joinDate;

    @Column(name = "ZEMP_TYPE", length = 4, nullable = false)
    private String empType;

    @Column(name = "ZLOC_CODE", length = 5, nullable = false)
    private String locCode;

    @Column(name = "ZDEPT_CD", length = 10, nullable = false)
    private String deptCode;

    @Column(name = "ZGRADE_CD", length = 5, nullable = false)
    private String gradeCode;

    @Column(name = "ZPOS_CD", length = 5)
    private String posCode;

    @Column(name = "ZSTATUS", length = 2, nullable = false)
    private String status;

    @Column(name = "ZRULE_ID", length = 10)
    private String ruleId;

    @Column(name = "ZEFF_DATE")
    private LocalDate effDate;

    @Column(name = "ZREMARK", length = 255)
    private String remark;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update (EmployeePersonalRequestDto dto) {
    	this.joinDate = dto.getJoinDate();
    	this.empType = dto.getEmpType();
    	this.locCode = dto.getLocCode();
    	this.deptCode = dto.getDeptCode();
    	this.gradeCode = dto.getGradeCode();
    	this.posCode = dto.getPosCode();
    	this.status = dto.getStatus();
    	this.ruleId = dto.getRuleId();
    	this.effDate = dto.getEffDate();
    	this.remark = dto.getRemark();
    }
}
