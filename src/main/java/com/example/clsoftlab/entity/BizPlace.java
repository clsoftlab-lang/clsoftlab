package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.hr.BizPlaceRequestDto;

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
@Table(name = "ZHR_BIZPLACE")
public class BizPlace extends BaseEntity{

    @Id
    @Column(name = "ZBIZ_CD", nullable = false, length = 10)
    private String bizCode;

    @Column(name = "ZBIZ_NM", nullable = false, length = 100)
    private String bizName;

    @Column(name = "ZADDR", nullable = false, length = 200)
    private String address;

    @Column(name = "ZREG_NO", length = 20)
    private String regNo;

    @Column(name = "ZTEL", length = 20)
    private String tel;

    @Column(name = "ZEMAIL", length = 50)
    private String email;

    @Column(name = "ZWORK_HOUR", length = 20)
    private String workHour;

    @Column(name = "ZWORK_DAYS", length = 10)
    private String workDays;

    @Column(name = "ZTYPE", length = 20)
    private String type;

    @Column(name = "ZMANAGER_ID", length = 12)
    private String managerId;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZREMARK", length = 200)
    private String remark;
    
    public void update (BizPlaceRequestDto dto) {
    	this.bizName = dto.getBizName();
    	this.address = dto.getAddress();
    	this.regNo = dto.getRegNo();
    	this.tel = dto.getTel();
    	this.email = dto.getEmail();
    	this.workHour = dto.getWorkHour();
    	this.workDays = dto.getWorkDays();
    	this.type = dto.getType();
    	this.managerId = dto.getManagerId();
    	this.useYn = dto.getUseYn();
    	this.remark = dto.getRemark();
    }
}
