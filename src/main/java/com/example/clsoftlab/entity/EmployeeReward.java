package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.EmployeeRewardRequestDto;

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
@Table(name = "ZHR_EMP_REWARD")
public class EmployeeReward extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REWARD_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @Column(name = "ZSEQ", nullable = false)
    private Integer seq;

    @Column(name = "ZAWARD_DATE", nullable = false)
    private LocalDate awardDate;

    @Column(name = "ZAWARD_NAME", nullable = false, length = 100)
    private String awardName;

    @Column(name = "ZAWARD_TYPE", nullable = false, length = 1)
    private String awardType;

    @Column(name = "ZAWARD_ORG", nullable = false, length = 100)
    private String awardOrg;

    @Column(name = "ZREASON", nullable = false, length = 200)
    private String reason;

    @Column(name = "ZCONTENT", length = 100)
    private String content;

    @Column(name = "ZATTACH_ID", length = 100)
    private String attachId;

    @Column(name = "ZREMARK", length = 200)
    private String remark;
    
    public void update (EmployeeRewardRequestDto dto) {
    	this.seq = dto.getSeq();
    	this.awardDate = dto.getAwardDate();
    	this.awardName = dto.getAwardName();
    	this.awardType = dto.getAwardType();
    	this.awardOrg = dto.getAwardOrg();
    	this.reason = dto.getReason();
    	this.content = dto.getContent();
    	this.attachId = dto.getAttachId();
    	this.remark = dto.getRemark();
    }
}
