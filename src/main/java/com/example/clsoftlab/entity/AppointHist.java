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
@Table(name = "ZHR_APPOINT_HIST")
public class AppointHist extends BaseEntity {

	@Id
    @Column(name = "ZHIST_ID", length = 20, nullable = false)
    private String id; // 발령 이력 ID (PK)

    @Column(name = "ZREQ_ID", length = 20, nullable = false)
    private String reqId; // 발령 품의 ID (Header 참조)

    @Column(name = "PERNR", length = 20, nullable = false)
    private String pernr; // 대상자 사번

    @Column(name = "ZRULE_ID", length = 10, nullable = false)
    private String ruleId; // 적용된 발령 기준 ID

    @Column(name = "ZEFF_DATE", nullable = false)
    private LocalDate effDate; // 발령 시행일

    @Column(name = "ZREMARK", length = 300)
    private String remark; // 비고

    @ColumnDefault("'N'")
    @Column(name = "IS_CANCELED", length = 1, nullable = false)
    private String isCanceled; // 발령 철회 여부

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    private Integer version; // 낙관적 락
}
