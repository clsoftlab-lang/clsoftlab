package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.OrgUnitRequestDto;

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
@Table(name = "ZHR_ORG_UNIT")
public class OrgUnit extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
    @Column(name = "ZORG_CD", length = 10, nullable = false, unique = true)
    private String orgCode;
    
    @Column(name = "ZORG_NM", nullable = false, length = 100)
    private String orgName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ORG_CD", referencedColumnName = "ZORG_CD")
    private OrgUnit parentOrgUnit;

    @Column(name = "ZORG_TYPE", nullable = false, length = 10)
    private String orgType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZBIZ_CD", referencedColumnName = "ZBIZ_CD", nullable = false)
    private BizPlace bizPlace;

    @Column(name = "ZVALID_FROM", nullable = false)
    private LocalDate validFrom;

    @Column(name = "ZVALID_TO")
    private LocalDate validTo;

    @Column(name = "ZMAIN_POS", length = 20)
    private String mainPos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZMANAGER_ID")
    private EmployeeMaster manager;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZREMARK", length = 200)
    private String remark;
    
    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;
    
    public void update (OrgUnitRequestDto dto) {
    	this.orgName = dto.getOrgName();
    	this.orgType = dto.getOrgType();
    	this.validFrom = dto.getValidFrom();
    	this.validTo = dto.getValidTo();
    	this.mainPos = dto.getMainPos();
    	this.useYn = dto.getUseYn();
    	this.remark = dto.getRemark();
    }
}
