package com.example.clsoftlab.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "ZHR_APPOINT_FIELDDEF")
public class AppointFieldDef extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FIELDDEF_ID", nullable = false)
    private Long id; // 대리키 (Auto Increment)

    @Column(name = "ZRULE_TYPE", length = 20, nullable = false)
    private String ruleType; // 발령 유형 (FK 개념, 공통코드)

    @Column(name = "ZFIELD_CD", length = 20, nullable = false)
    private String fieldCode; // 항목 코드 (TO_DEPT, TO_RANK 등)

    @Column(name = "ZFIELD_NM", length = 100, nullable = false)
    private String fieldName; // 항목 명칭 (화면 라벨)

    @ColumnDefault("'STRING'")
    @Column(name = "ZDATA_TYPE", length = 20, nullable = false)
    private String dataType; // 데이터 타입 (STRING, NUMBER, DATE, CODE)

    @ColumnDefault("'N'")
    @Column(name = "ZREQUIRED", length = 1, nullable = false)
    private String required; // 필수 입력 여부 (Y/N)

    @ColumnDefault("0")
    @Column(name = "ZORDER", nullable = false)
    private Integer order; // 화면 출력 순서

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    private Integer version;
}
