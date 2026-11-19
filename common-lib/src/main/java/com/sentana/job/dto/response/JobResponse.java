package com.sentana.job.dto.response;

import com.sentana.job.domain.ExperienceLevel;
import com.sentana.job.domain.JobStatus;
import com.sentana.job.domain.JobType;
import com.sentana.job.domain.WorkMode;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {
    private Long id;
    private String title;
    private String description;
    private String requirements;
    private String responsibilities;
    private String benefits;
    private CompanyResponse company;
    private String employerId;


    private JobCategoryResponse category;
    private Set<JobSkillResponse> skills;
    private Set<JobTagResponse> tags;

//    Location
    private String address;
    private String city;
    private String country;
    private String state;
    private String zipCode;

//    Salary
    private BigDecimal minSalary;
    private BigDecimal maxSalary;

//    Classification
    private JobType jobType;
    private WorkMode workMode;
    private ExperienceLevel experienceLevel;
    private JobStatus jobStatus;

//    Posting details
    private Integer openings;
    private LocalDate applicationDeadline;
    private LocalDate expiresAt;
    private Boolean active;

//    Timestamp
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
    private LocalDateTime closedAt;


}
