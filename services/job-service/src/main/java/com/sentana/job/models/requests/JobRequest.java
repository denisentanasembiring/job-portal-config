package com.sentana.job.models.requests;

import com.sentana.job.domain.ExperienceLevel;
import com.sentana.job.domain.JobStatus;
import com.sentana.job.domain.JobType;
import com.sentana.job.domain.WorkMode;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
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
public class JobRequest {

    @NotBlank(message = "job title is require")
    private String title;

    @NotBlank(message = "description is require")
    private String description;

    private String requirements;
    private String responsibilities;
    private String benefits;

    @NotNull(message = "category is required")
    private Long categoryId;

    private Set<Long> skillIds;

    private Set<Long> tagIds;

//    Location
    private String address;
    private String city;
    private String country;
    private String state;
    private String zipCode;

//    Salary
    @DecimalMin(value = "0.0", message = "min salary must not be negative")
    private BigDecimal minSalary;

    @DecimalMax(value = "0.0", message = "max salary must not be negative")
    private BigDecimal maxSalary;

//    Classification
    @NotNull(message = "job type is required")
    private JobType jobType;

    @NotNull(message = "work mode is required")
    private WorkMode workMode;

    @NotNull(message = "experience level is required")
    private ExperienceLevel experienceLevel;

    @NotNull(message = "job status")
    private JobStatus jobStatus;

    @Min(value = 1, message = "openings must be at least 1" )
    private Integer openings = 1;

    @NotNull(message = "application deadline is required")
    private LocalDate applicationDeadline;

    private LocalDate expiredAt;

}
