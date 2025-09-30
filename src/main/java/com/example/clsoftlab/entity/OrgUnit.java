package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.OrgUnitRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "ZHR_ORG_UNIT")
public class OrgUnit extends BaseEntity {

    @Id
    @Column(name = "ZORG_CD", length = 10)
    private String orgCode;
    
    @Column(name = "ZORG_NM", nullable = false, length = 100)
    private String orgName;
    
    @Column(name = "PARENT_ORG_CD", length = 10)
    private String parentOrgCode;

    @Column(name = "ZORG_TYPE", nullable = false, length = 10)
    private String orgType;

    @Column(name = "ZBIZ_CD", nullable = false, length = 10)
    private String bizCode;

    @Column(name = "ZVALID_FROM", nullable = false)
    private LocalDate validFrom;

    @Column(name = "ZVALID_TO")
    private LocalDate validTo;

    @Column(name = "ZMAIN_POS", length = 20)
    private String mainPos;

    @Column(name = "ZMANAGER_ID", length = 12)
    private String managerId;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZREMARK", length = 200)
    private String remark;
    
    public void update (OrgUnitRequestDto dto) {
    	this.orgName = dto.getOrgName();
    	this.parentOrgCode = dto.getParentOrgCode();
    	this.orgType = dto.getOrgType();
    	this.validFrom = dto.getValidFrom();
    	this.validTo = dto.getValidTo();
    	this.mainPos = dto.getMainPos();
    	this.managerId = dto.getManagerId();
    	this.useYn = dto.getUseYn();
    	this.remark = dto.getRemark();
    }
}
