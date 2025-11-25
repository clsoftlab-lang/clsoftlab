package com.example.clsoftlab.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ZSYS_USER_ACCOUNT")
public class UserAccount extends BaseEntity {

	// --- [1] 식별자 ---
    @Id
    @Column(name = "USER_ID", length = 50, nullable = false)
    private String userId;

    // --- [2] 기본 정보 ---
    @Column(name = "PERNR", length = 10)
    private String pernr;

    @Column(name = "USER_NM", length = 50, nullable = false)
    private String userName;

    @Column(name = "PASSWD", length = 255, nullable = false)
    private String password;

    // --- [3] 상태 및 보안 관리 ---
    @Column(name = "LOGIN_FAIL_CNT", nullable = false)
    @ColumnDefault("0")
    private Integer loginFailCnt = 0;

    @Column(name = "IS_LOCKED", length = 1, nullable = false)
    @ColumnDefault("'N'")
    private String isLocked = "N";

    @Column(name = "IS_USE", length = 1, nullable = false)
    @ColumnDefault("'Y'")
    private String isUse = "Y";

    // --- [4] 이력 및 감사 ---
    @Column(name = "LAST_LOGIN_DT")
    private LocalDateTime lastLoginDate;
}
