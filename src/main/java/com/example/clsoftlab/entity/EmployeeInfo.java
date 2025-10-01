package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.EmployeeInfoRequestDto;

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
@Table(name = "ZHR_EMP_INFO")
public class EmployeeInfo extends BaseEntity {

   @Id
   @Column(name = "PERNR", nullable = false, length = 10)
   private String pernr;
   
   @Column(name = "ZHIRE_DATE", nullable = false)
   private LocalDate hireDate;

   @Column(name = "ZEMP_TYPE", nullable = false, length = 10)
   private String empType;

   @Column(name = "ZWORK_TYPE", nullable = false, length = 10)
   private String workType;

   @Column(name = "ZBIZ_CD", nullable = false, length = 10)
   private String bizCode;

   @Column(name = "ZDEPT_CD", nullable = false, length = 10)
   private String deptCode;

   @Column(name = "ZPOS_CD", nullable = false, length = 10)
   private String posCode;

   @Column(name = "ZGRADE_CD", nullable = false, length = 10)
   private String gradeCode;

   @Column(name = "ZDEPT_JOIN", nullable = false)
   private LocalDate deptJoin;

   @Column(name = "ZEMP_STATUS", nullable = false, length = 2)
   private String empStatus;

   @Column(name = "ZLAST_APPT_DATE", nullable = false)
   private LocalDate lastApptDate;
   
   public void update (EmployeeInfoRequestDto dto) {
	   this.hireDate = dto.getHireDate();
	   this.empType = dto.getEmpType();
	   this.workType = dto.getWorkType();
	   this.bizCode = dto.getBizCode();
	   this.deptCode = dto.getDeptCode();
	   this.posCode = dto.getPosCode();
	   this.gradeCode = dto.getGradeCode();
	   this.deptJoin = dto.getDeptJoin();
	   this.lastApptDate = dto.getLastApptDate();
	   this.empStatus = dto.getEmpStatus();
   }
}
