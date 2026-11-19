package com.sentana.job.models.requests;

import com.sentana.job.domain.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceRequest {

    @NotBlank(message = "company name is required")
    private String companyName;

    private String companyLogoUrl;

    @NotBlank(message = "job title is required")
    private String jobTitle;

    private JobType employmentType;

    private String location;

    @NotNull(message = "start date is required")
    private LocalDate startDate;

    private LocalDate endDate;


    private Boolean isCurrentJob = false;

    private String description;

    private List<String> technologies;

    private Integer displayOrder;
}
