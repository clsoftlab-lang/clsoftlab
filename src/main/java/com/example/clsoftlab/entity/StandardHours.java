package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.pay.StandardHoursRequestDto;

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
@Table(name = "ZHR_STD_HOURS")
public class StandardHours extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STD_HOURS_ID", nullable = false)
    private Long id;
	
    @Column(name = "ZCAL_YM", nullable = false, length = 6)
    private String calYm;

    @Column(name = "ZJOB_GRP", length = 10)
    private String jobGroup;

    @Column(name = "ZSTD_HOURS", nullable = false)
    private Integer standardHours;

    @Column(name = "ZNOTE", length = 500)
    private String note;

    public void update (StandardHoursRequestDto dto) {
    	this.standardHours = dto.getStandardHours();
    	this.note = dto.getNote();
    }

}
