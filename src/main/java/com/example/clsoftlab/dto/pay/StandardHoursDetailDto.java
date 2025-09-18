package com.example.clsoftlab.dto.pay;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardHoursDetailDto {

	private long id;
    private String calYm; 
    private String jobGroup; 
    private Integer standardHours; 
    private String note; 
    private LocalDateTime createdAt;
}
