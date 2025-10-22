package com.example.clsoftlab.entity;

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
@Table(name = "ZHR_EMP_MASTER")
public class EmployeeMaster extends BaseEntity {

    @Id
    @Column(name = "PERNR", length = 10, nullable = false)
    private String pernr;

    @Column(name = "ENAME", length = 50, nullable = false)
    private String name;
    
}
