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
@Table(name = "ZHR_APPOINT_REQ")
public class AppointReq extends BaseEntity {

	@Id
    @Column(name = "ZREQ_ID", length = 20, nullable = false)
    private String id; // 발령 품의 ID (PK)

    @Column(name = "ZTITLE", length = 100, nullable = false)
    private String title; // 품의 제목

    @Column(name = "ZDESC", length = 500)
    private String desc; // 품의 내용 및 사유

    @Column(name = "ZREQ_DATE", nullable = false)
    private LocalDate reqDate; // 기안 일자

    @Column(name = "ZREQ_BY", length = 20, nullable = false)
    private String reqBy; // 기안자 사번

    @ColumnDefault("'10'")
    @Column(name = "ZSTATUS", length = 10, nullable = false)
    private String status; // 진행 상태 (공통코드 HR_APPR_STAT)

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    private Integer version; // 낙관적 락
}
