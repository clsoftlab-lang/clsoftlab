package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.UnionMemberRequestDto;

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
@Table(name = "ZHR_UNION_MEMBER")
public class UnionMember extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNION_HISTORY_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr; 

    @Column(name = "ZJOIN_DATE", nullable = false)
    private LocalDate joinDate;

    @Column(name = "ZUNION_YN", nullable = false, length = 1)
    private String unionYn; 

    @Column(name = "ZLEAVE_DATE")
    private LocalDate leaveDate;

    @Column(name = "ZUNION_TYPE", nullable = false, length = 5)
    private String unionType; 

    @Column(name = "ZCLUB_CD", nullable = false, length = 10)
    private String clubCode; 

    @Column(name = "ZFEE", nullable = false, precision = 15, scale = 2)
    private BigDecimal fee; 

    @Column(name = "ZAUTO_DEDUCT", nullable = false, length = 1)
    private String autoDeduct; 

    @Column(name = "ZREMARK", length = 255)
    private String remark; 
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update (UnionMemberRequestDto dto) {
    	this.joinDate = dto.getJoinDate();
    	this.unionType = dto.getUnionType();
    	this.unionYn = dto.getUnionYn();
    	this.leaveDate = dto.getLeaveDate();
    	this.clubCode = dto.getClubCode();
    	this.fee = dto.getFee();
    	this.autoDeduct = dto.getAutoDeduct();
    	this.remark = dto.getRemark();
    }
}
