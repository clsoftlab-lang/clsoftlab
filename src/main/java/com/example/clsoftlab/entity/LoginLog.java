package com.example.clsoftlab.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 무분별한 객체 생성 방지
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "ZSYS_LOGIN_LOG")
public class LoginLog {

	public static final String TYPE_GENERAL = "GENERAL";
    public static final String TYPE_SSO     = "SSO";
    public static final String TYPE_COOKIE  = "COOKIE";
    
    public static final String RESULT_SUCCESS = "SUCCESS";
    public static final String RESULT_FAIL    = "FAIL";
    public static final String RESULT_LOCKED  = "LOCKED";

    // --- [1] 로그 식별자 ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID")
    private Long id;

    // --- [2] 로그인 시도 정보 ---
    @Column(name = "LOGIN_ID", length = 50, nullable = false)
    private String loginId;

    @Column(name = "LOGIN_TYPE", length = 20, nullable = false)
    private String loginType; // GENERAL, SSO, COOKIE

    // --- [3] 접속 환경 정보 ---
    @Column(name = "IP_ADDRESS", length = 50, nullable = false)
    private String ipAddress;

    @Column(name = "USER_AGENT", length = 500)
    private String userAgent;

    // --- [4] 로그인 결과 ---
    @Column(name = "LOGIN_RESULT", length = 10, nullable = false)
    private String loginResult; // SUCCESS, FAIL, LOCKED

    @Column(name = "FAIL_REASON", length = 1000)
    private String failReason;

    // --- [5] 발생 일시 ---
    @CreationTimestamp // INSERT 시 시간 자동 저장
    @Column(name = "LOGIN_DT", nullable = false, updatable = false)
    private LocalDateTime loginDate;

}
