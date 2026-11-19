package com.sentana.job.mappers;

import com.sentana.job.dto.response.JobTagResponse;
import com.sentana.job.models.entities.JobTag;

public class JobTagMapper {
    public static JobTagResponse toJobTagResponse(JobTag tag){
        return JobTagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .build();

    }
}
