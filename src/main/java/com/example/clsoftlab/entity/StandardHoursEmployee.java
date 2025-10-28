package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.pay.StandardHoursEmployeeRequestDto;

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
@Table(name = "ZHR_STD_HOURS_EMP")
public class StandardHoursEmployee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STD_HOURS_EMP_ID")
    private Long id;

    @Column(name = "ZCAL_YM", nullable = false, length = 6)
    private String calYm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZEMP_NO", referencedColumnName = "PERNR", nullable = false)
    private EmployeeMaster employee;

    @Column(name = "ZSTD_HOURS", nullable = false)
    private Integer standardHours;

    @Column(name = "ZREASON_CD", length = 20)
    private String reasonCode;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update (StandardHoursEmployeeRequestDto dto) {
    	this.standardHours = dto.getStandardHours();
    	this.reasonCode = dto.getReasonCode();
    	this.note = dto.getNote();
    }

}
