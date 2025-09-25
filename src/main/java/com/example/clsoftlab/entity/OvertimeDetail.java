package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.clsoftlab.dto.pay.OvertimeDetailRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "ZHR_OVERTIME_DETAIL")
public class OvertimeDetail extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ZHR_OVERTIME_DETAIL_ID")
    private Long id;

    @Column(name = "ZEMP_NO", nullable = false, length = 50)
    private String empNo;

    @Column(name = "ZDATE", nullable = false)
    private LocalDate date;

    @Column(name = "ZHOURS", nullable = false, precision = 5, scale = 2)
    private BigDecimal hours;

    @Column(name = "ZTYPE", nullable = false, length = 10)
    private String type;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    public void update (OvertimeDetailRequestDto dto) {
    	this.date = dto.getDate();
    	this.hours = dto.getHours();
    	this.type = dto.getType();
    	this.note = dto.getNote();
    }
}
