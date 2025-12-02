package com.example.clsoftlab.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
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
@DynamicInsert
@DynamicUpdate
@Table(name = "ZHR_APPOINT_DETAIL")
public class AppointDetail extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID", nullable = false)
    private Long id; // 발령 상세 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZHIST_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private AppointHist appointHist;

    @Column(name = "ZFIELD_CD", length = 20, nullable = false)
    private String fieldCode; // 항목 코드 (ZHR_APPOINT_FIELDDEF 참조)

    @Column(name = "ZVALUE_OLD")
    private String valueOld; // 변경 전 값

    @Column(name = "ZVALUE")
    private String value; // 변경 후 값

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    private Integer version; // 낙관적 락
}
