package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.EmployeeWorkplaceRequestDto;

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
@Table(name = "ZHR_EMP_WORKPLACE")
public class EmployeeWorkplace extends BaseEntity{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKPLACE_HISTORY_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZWORK_LOC_CD", nullable = false) 
    private BizPlace bizplace;

    @Column(name = "ZSTART_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "ZEND_DATE")
    private LocalDate endDate;

    @Column(name = "ZCURRENT_YN", nullable = false, length = 1)
    private String currentYn;

    @Column(name = "ZREMARK", length = 255)
    private String remark;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update (EmployeeWorkplaceRequestDto dto) {
    	this.startDate = dto.getStartDate();
    	this.endDate = dto.getEndDate();
    	this.currentYn = dto.getCurrentYn();
    	this.remark = dto.getRemark();
    }
}
