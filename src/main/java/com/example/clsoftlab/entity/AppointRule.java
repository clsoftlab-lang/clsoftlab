package com.example.clsoftlab.entity;

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
@Table(name = "ZHR_APPOINT_RULE")
public class AppointRule extends BaseEntity {

	@Id
    @Column(name = "ZRULE_ID", length = 10, nullable = false)
    private String id;

    @Column(name = "ZRULE_NM", length = 100, nullable = false)
    private String ruleName;

    @Column(name = "ZRULE_TYPE", length = 20, nullable = false)
    private String ruleType;

    @Column(name = "ZCOND_DESC", length = 200)
    private String condDesc;

    @ColumnDefault("'Y'")
    @Column(name = "ZUSE_YN", length = 1, nullable = false)
    private String useYn;

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    private Integer version;
}
