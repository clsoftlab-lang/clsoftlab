package com.example.clsoftlab.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZREQ_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private AppointReq appointReq;

    // 2. [참조] 발령 기준 (Rule) 연결 (화면 출력용)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZRULE_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private AppointRule appointRule;

    // 3. [자식] 발령 상세 (Detail) 리스트
    @OneToMany(mappedBy = "appointHist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointDetail> details = new ArrayList<>();

    // 4. [대상자] 사원 (Employee) 연결 (이름, 부서 등 조회용)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERNR", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private EmployeeMaster employee;


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
