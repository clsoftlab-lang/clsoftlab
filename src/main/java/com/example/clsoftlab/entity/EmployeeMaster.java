package com.example.clsoftlab.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicInsert
@DynamicUpdate
@Table(name = "ZHR_EMP_MASTER")
public class EmployeeMaster extends BaseEntity {

	@Id
    @Column(name = "PERNR", length = 10, nullable = false)
    private String pernr;

    @Column(name = "ENAME", length = 50, nullable = false)
    private String name;
    
    @Column(name = "ENTRY_DT", nullable = false)
    private LocalDate entryDate;
    
    @Column(name = "RETIRE_DT")
    private LocalDate retireDate;
    
    @Column(name = "RANK_CD", length = 20, nullable = false)
    private String rankCode;

    @Column(name = "DUTY_CD", length = 20, nullable = false)
    private String dutyCode;

    @Column(name = "EMP_STATUS", length = 20, nullable = false)
    @ColumnDefault("'10'") // 재직
    private String empStatus = "10";

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    private Integer version;
    
    
}
