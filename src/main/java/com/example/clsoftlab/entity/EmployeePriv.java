package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.EmployeePrivRequestDto;

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
@Table(name = "ZHR_EMP_PRIV")
public class EmployeePriv extends BaseEntity {

	@Id
    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @Column(name = "ENAME", nullable = false, length = 50)
    private String name;
    
    @Column(name = "GENDER", nullable = false, length = 1)
    private String gender;

    @Column(name = "BIRTHDATE", nullable = false)
    private LocalDate birthdate;

    @Column(name = "SSN", nullable = false, length = 255)
    private String ssn;

    @Column(name = "NATIONALITY", nullable = false, length = 30)
    private String nationality;

    @Column(name = "PHONE_NO", nullable = false, length = 20)
    private String phoneNo;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "ADDR", length = 255)
    private String addr;

    @Column(name = "MARITAL_STATUS", length = 10)
    private String maritalStatus;

    @Column(name = "MILITARY_INFO", length = 100)
    private String militaryInfo;

    @Column(name = "DISABILITY_YN", length = 1)
    private String disabilityYn;

    @Column(name = "EMERGENCY_NAME", length = 50)
    private String emergencyName;

    @Column(name = "EMERGENCY_REL", length = 20)
    private String emergencyRel;

    @Column(name = "EMERGENCY_PHONE", length = 20)
    private String emergencyPhone;
    
    public void update (EmployeePrivRequestDto dto) {
    	this.name = dto.getName();
    	this.gender = dto.getGender();
    	this.birthdate = dto.getBirthdate();
    	this.phoneNo = dto.getPhoneNo();
    	this.email = dto.getEmail();
    	this.addr = dto.getAddr();
    	this.maritalStatus = dto.getMaritalStatus();
    	this.militaryInfo = dto.getMilitaryInfo();
    	this.disabilityYn = dto.getDisabilityYn();
    	this.emergencyName = dto.getEmergencyName();
    	this.emergencyRel = dto.getEmergencyRel();
    	this.emergencyPhone = dto.getEmergencyPhone();
    }
}
