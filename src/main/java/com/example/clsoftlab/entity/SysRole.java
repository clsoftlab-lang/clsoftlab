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
@Table(name = "ZSYS_ROLE")
public class SysRole extends BaseEntity {

	@Id
    @Column(name = "ROLE_ID", length = 20, nullable = false)
    private String id; // 예: ADMIN, USER

    @Column(name = "ROLE_NM", length = 100, nullable = false)
    private String roleName;

    @Column(name = "ROLE_DESC", length = 300)
    private String roleDesc;

    @Column(name = "ZORDER", nullable = false)
    @ColumnDefault("0")
    private Integer order;

    @Column(name = "ZUSE_YN", length = 1, nullable = false)
    @ColumnDefault("'Y'")
    private String useYn;

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    private Integer version;
}
