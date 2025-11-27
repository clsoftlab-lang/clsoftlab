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
import lombok.Builder;
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
@Builder
@Table(name = "ZSYS_CODE_GROUP")
public class CodeGroup extends BaseEntity {

	@Id
    @Column(name = "ZGROUP_ID", length = 20, nullable = false)
    private String id;

    @Column(name = "ZGROUP_NM", length = 100, nullable = false)
    private String groupName;

    @Builder.Default
    @Column(name = "ZUSE_YN", length = 1, nullable = false)
    @ColumnDefault("'Y'")
    private String useYn = "Y";

    @Builder.Default
    @Column(name = "ZORDER", nullable = false)
    @ColumnDefault("0")
    private Integer order = 0;

    @Column(name = "ZREMARK", length = 300)
    private String remark;

    @Builder.Default
    @Column(name = "IS_SYSTEM", length = 1, nullable = false)
    @ColumnDefault("'N'")
    private String isSystem = "N";

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;
}
