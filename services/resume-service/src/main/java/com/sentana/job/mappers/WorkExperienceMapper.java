package com.sentana.job.mappers;

import com.sentana.job.dto.response.WorkExperienceResponse;
import com.sentana.job.models.entities.WorkExperience;

public class WorkExperienceMapper {
    public static WorkExperienceResponse toExperienceResponse(WorkExperience exp){
        return WorkExperienceResponse.builder()
                .id(exp.getId())
                .companyName(exp.getCompanyName())
                .companyLogoUrl(exp.getCompanyLogUrl())
                .jobTitle(exp.getJobTitle())
                .employmentType(exp.getEmploymentType())
                .location(exp.getLocation())
                .startDate(exp.getStartDate())
                .endData(exp.getEndDate())
                .isCurrentJob(exp.getIsCurrentJob())
                .description(exp.getDescription())
                .technologies(exp.getTechnologies())
                .displayOrder(exp.getDisplayOrder())
                .build();
    }
}
