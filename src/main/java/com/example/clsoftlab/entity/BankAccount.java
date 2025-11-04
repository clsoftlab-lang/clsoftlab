package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.converter.CryptoConverter;
import com.example.clsoftlab.dto.pay.BankAccountRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "ZHR_BANK_ACCOUNT")
public class BankAccount extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANK_ACCOUNT_ID")
    private Long id;

	@ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "ZEMP_NO",referencedColumnName = "PERNR", nullable = false)
    private EmployeeMaster employee;

    @Column(name = "ZACC_TYPE", nullable = false, length = 10)
    private String accountType;

    @Column(name = "ZBANK_CD", nullable = false, length = 10)
    private String bankCode;

    @Column(name = "ZBANK_NAME", nullable = false, length = 100)
    private String bankName;

    @Convert(converter = CryptoConverter.class)
    @Column(name = "ZACC_NO", nullable = false, length = 255)
    private String accountNo;
    
    @Column(name = "ZACC_NO_HASH")
    private String accountNoHash;

    @Column(name = "ZACC_HOLDER", nullable = false, length = 100)
    private String accountHolder;

    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate;

    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update (BankAccountRequestDto dto) {
    	this.fromDate = dto.getFromDate();
    	this.toDate = dto.getToDate();
    	this.useYn = dto.getUseYn();
    	this.note = dto.getNote();
    }

}
