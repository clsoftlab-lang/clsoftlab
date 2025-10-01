package com.example.clsoftlab.dto.hr;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePrivLogDetailDto {

    private Long id;
    private String pernr;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private String changeType;
    private LocalDateTime createdAt;
    private String createdBy;
}
