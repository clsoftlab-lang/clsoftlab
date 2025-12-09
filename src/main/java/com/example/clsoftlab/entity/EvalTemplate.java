package com.example.clsoftlab.entity;

import java.time.LocalDate;

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
@Table(name = "ZHR_EVAL_TEMPLATE")
public class EvalTemplate extends BaseEntity {

	@Id
    @Column(name = "TEMPLATE_ID", length = 20)
    private String templateId;

    @Column(name = "TEMPLATE_NM", nullable = false, length = 100)
    private String templateName;

    @Column(name = "ZYEAR", nullable = false, length = 4)
    private String year;

    @Column(name = "ZEV_TYPE", nullable = false, length = 10)
    private String evType;

    @Column(name = "ZSTATUS", length = 1)
    private String status;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;
    
    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;
}
