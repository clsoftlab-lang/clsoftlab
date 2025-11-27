package com.example.clsoftlab.entity;

import java.time.LocalDate;

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
@Table(name = "ZSYS_CODE_DETAIL")
public class CodeDetail extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID")
    private Long id; 

    @Column(name = "ZGROUP_ID", length = 20, nullable = false)
    private String groupId;

    @Column(name = "ZCODE", length = 20, nullable = false)
    private String code;

    @Column(name = "ZNAME", length = 100, nullable = false)
    private String name;

    @Column(name = "ZNAME_EN", length = 100)
    private String nameEn;

    @Column(name = "START_DT", nullable = false)
    @ColumnDefault("'1900-01-01'")
    private LocalDate startDate = LocalDate.of(1900, 1, 1);

    @Column(name = "END_DT", nullable = false)
    @ColumnDefault("'9999-12-31'")
    private LocalDate endDate = LocalDate.of(9999, 12, 31);

    @Column(name = "ZUSE_YN", length = 1, nullable = false)
    @ColumnDefault("'Y'")
    private String useYn = "Y";
    
    @Column(name = "ZORDER", nullable = false)
    @ColumnDefault("0")
    private Integer order = 0;

    @Column(name = "ZREMARK", length = 300)
    private String remark;

    @Column(name = "IS_SYSTEM", length = 1, nullable = false)
    @ColumnDefault("'N'")
    private String isSystem = "N";

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    private Integer version;
}
