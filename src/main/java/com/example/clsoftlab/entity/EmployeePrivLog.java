package com.example.clsoftlab.entity;

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
@Table(name = "ZHR_EMP_PRIV_LOG")
public class EmployeePrivLog extends BaseEntityWithOutUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @Column(name = "FIELD_NAME", nullable = false, length = 50)
    private String fieldName;

    @Column(name = "OLD_VALUE", length = 500)
    private String oldValue;

    @Column(name = "NEW_VALUE", length = 500)
    private String newValue;

    @Column(name = "CHANGE_TYPE", nullable = false, length = 10)
    private String changeType;
}
