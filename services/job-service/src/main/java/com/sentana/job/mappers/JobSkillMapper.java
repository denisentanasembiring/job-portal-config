package com.sentana.job.mappers;

import com.sentana.job.dto.response.JobSkillResponse;
import com.sentana.job.models.entities.JobSkill;

public class JobSkillMapper {
    public static JobSkillResponse toJobSkillResponse(JobSkill skill){
        return JobSkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .slug(skill.getSlug())
                .skillCategory(skill.getSkillCategory())
                .active(skill.getActive())
                .build();
    }
}
