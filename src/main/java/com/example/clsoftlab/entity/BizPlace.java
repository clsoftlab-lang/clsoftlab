package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.hr.BizPlaceRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "ZHR_BIZPLACE")
public class BizPlace extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ZID")
	private Long id;

    @Column(name = "ZBIZ_CD", nullable = false, length = 10 , unique = true)
    private String bizCode;

    @Column(name = "ZBIZ_NM", nullable = false, length = 100)
    private String bizName;

    @Column(name = "ZPOSTCODE", length = 5)
    private String postcode; // 우편번호

    @Column(name = "ZSIDO", length = 50)
    private String sido; // 시/도 (검색용)

    @Column(name = "ZSIGUNGU", length = 50)
    private String sigungu; // 시/군/구 (검색용, '성남시 분당구' -> '성남시')

    @Column(name = "ZADDR_MAIN", nullable = false, length = 200)
    private String addrMain; // 기본 주소 (Kakao API 반환값)

    @Column(name = "ZADDR_DETAIL", length = 100)
    private String addrDetail; // 상세 주소 (사용자 입력)

    @Column(name = "ZREG_NO", length = 20)
    private String regNo;

    @Column(name = "ZTEL", length = 20)
    private String tel;

    @Column(name = "ZEMAIL", length = 50)
    private String email;

    @Column(name = "ZWORK_HOUR", length = 20)
    private String workHour;

    @Column(name = "ZWORK_DAYS", length = 10)
    private String workDays;

    @Column(name = "ZTYPE", length = 20)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZMANAGER_ID", referencedColumnName = "PERNR")
    private EmployeeMaster manager;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZREMARK", length = 200)
    private String remark;
    
    @Version
    @Column(name = "ZVERSION", nullable = false)
    private Long version;
    
    public void update (BizPlaceRequestDto dto) {
    	this.bizName = dto.getBizName();
    	this.postcode = dto.getPostcode();
    	this.sido = dto.getSido();
    	this.sigungu = dto.getSigungu();
    	this.addrMain = dto.getAddrMain();
    	this.addrDetail = dto.getAddrDetail();
    	this.regNo = dto.getRegNo();
    	this.tel = dto.getTel();
    	this.email = dto.getEmail();
    	this.workHour = dto.getWorkHour();
    	this.workDays = dto.getWorkDays();
    	this.type = dto.getType();
    	this.useYn = dto.getUseYn();
    	this.remark = dto.getRemark();
    }
}
