package com.example.clsoftlab.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.clsoftlab.dto.hr.EmployeeRewardRequestDto;

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

    @Column(name = "ZREWARD_DATE", nullable = false)
    private LocalDate rewardDate;

    @Column(name = "ZREWARD_NAME", nullable = false, length = 100)
    private String rewardName;

    @Column(name = "ZREWARD_TYPE", nullable = false, length = 20)
    private String rewardType;

    @Column(name = "ZREWARD_ORG", nullable = false, length = 100)
    private String rewardOrg;

    @Column(name = "ZREASON", length = 200)
    private String reason;

    @ColumnDefault("0")
    @Column(name = "ZREWARD_AMT", nullable = false)
    private Long rewardAmt;

    @Column(name = "ZCONTENT", length = 100)
    private String content;

    @Column(name = "ZATTACH_ID", length = 100)
    private String attachId;

    @Column(name = "ZREMARK", length = 200)
    private String remark;
    
    // [추가] 낙관적 락
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update(EmployeeRewardRequestDto dto) {
    	this.seq = dto.getSeq();
    	this.rewardDate = dto.getRewardDate();
    	this.rewardName = dto.getRewardName();
    	this.rewardType = dto.getRewardType();
    	this.rewardOrg = dto.getRewardOrg();
    	this.reason = dto.getReason();
        this.rewardAmt = dto.getRewardAmt();
    	this.content = dto.getContent();
    	this.attachId = dto.getAttachId();
    	this.remark = dto.getRemark();
    }
}
