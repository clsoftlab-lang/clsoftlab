package com.example.clsoftlab.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.clsoftlab.dto.hr.EmployeeCareerRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@DynamicInsert
@DynamicUpdate
@Table(name = "ZHR_EMP_CAREER")
public class EmployeeCareer extends BaseEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAREER_ID")
    private Long id; // PK

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr; // 사번 (FK)

    @Column(name = "ZSEQ", nullable = false)
    private Integer seq; // 순번

    @Column(name = "ZCAREER_TYPE", nullable = false, length = 20)
    private String careerType; // 근무 형태 (HR_CAREER_TYPE)

    @Column(name = "ZCOMP_NAME", nullable = false, length = 100)
    private String companyName; // 회사명

    @Column(name = "ZDEPT_NAME", length = 100)
    private String deptName; // 근무 부서명

    @Column(name = "ZJOB_RANK", length = 50)
    private String jobRank; // 최종 직위/직급

    @Column(name = "ZJOB_DUTY", length = 100)
    private String jobDuty; // 담당 직무

    @Column(name = "ZJOB_DESC", length = 500)
    private String jobDesc; // 업무 상세 내용

    @Column(name = "ZSTART_DATE", nullable = false)
    private LocalDate startDate; // 입사일

    @Column(name = "ZEND_DATE", nullable = false)
    private LocalDate endDate; // 퇴사일

    @Column(name = "ZLAST_SALARY")
    private Long lastSalary;

    @Column(name = "ZRESIGN_REASON", length = 200)
    private String resignReason; // 퇴사 사유

    @ColumnDefault("100")
    @Column(name = "ZRECOG_RATIO")
    private Integer recogRatio;

    @Column(name = "ZATTACH_ID", length = 100)
    private String attachId; // 첨부파일 ID

    @Column(name = "ZREMARK", length = 200)
    private String remark; // 비고

    @Version
    @Column(name = "VERSION")
    private Long version; // 낙관적 락
    
	public void update(EmployeeCareerRequestDto dto) {
	    this.seq = dto.getSeq();
	    this.careerType = dto.getCareerType();
	    this.companyName = dto.getCompanyName();
	    this.deptName = dto.getDeptName();
	    
	    this.jobRank = dto.getJobRank();
	    this.jobDuty = dto.getJobDuty();
	    this.jobDesc = dto.getJobDesc();
	    
	    this.startDate = dto.getStartDate();
	    this.endDate = dto.getEndDate();
	    
	    this.lastSalary = dto.getLastSalary();
	    this.resignReason = dto.getResignReason();
	    this.recogRatio = dto.getRecogRatio();
	    
	    this.attachId = dto.getAttachId();
	    this.remark = dto.getRemark();
	}
}
