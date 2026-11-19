package com.sentana.job.models.requests;

import com.sentana.job.domain.AiSortListStatus;
import com.sentana.job.domain.ApplicationStatus;
import lombok.Data;

@Data
public class CompanyApplicationFilterRequest {

    private Long jobId;

    private ApplicationStatus status;

    private Boolean isStarred;

    private AiSortListStatus aiSortListStatus;

    private Integer minAiScore;

    private String sortBy;
}
