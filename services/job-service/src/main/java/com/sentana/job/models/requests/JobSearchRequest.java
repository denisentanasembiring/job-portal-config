package com.sentana.job.models.requests;

import com.sentana.job.domain.ExperienceLevel;
import com.sentana.job.domain.JobStatus;
import com.sentana.job.domain.JobType;
import com.sentana.job.domain.WorkMode;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchRequest {
    private String keyword;
    private Long categoryId;
    private List<Long> skillIds;
    private List<Long> tagIds;
    private Long companyId;
    private String location;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private JobType jobType;
    private WorkMode workMode;
    private ExperienceLevel experienceLevel;
    private JobStatus jobStatus;
    private Integer minOpenings;
    private Integer maxOpenings;
}
