package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.pay.EmployeeAllowRequestDto;

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
@Table(name = "ZHR_EMP_ALLOW")
public class EmployeeAllow extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALLOW_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 권장
    @JoinColumn(name = "ZEMP_NO", nullable = false) // 실제 FK 컬럼명
    private EmployeeMaster employeeMaster;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 권장
    @JoinColumn(name = "ZITEM_CD", nullable = false) // 실제 FK 컬럼명
    private PayItem payItem;

    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate;

    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate;

    @Column(name = "ZALLOW_AMT", nullable = false)
    private Long allowAmt;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update (EmployeeAllowRequestDto dto) {
    	this.fromDate = dto.getFromDate();
    	this.toDate = dto.getToDate();
    	this.allowAmt = dto.getAllowAmt();
    	this.useYn = dto.getUseYn();
    	this.note = dto.getNote();
    }
}
