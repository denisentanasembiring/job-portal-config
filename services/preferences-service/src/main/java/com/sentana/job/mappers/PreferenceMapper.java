package com.sentana.job.mappers;

import com.sentana.job.dto.response.SavedJobResponse;
import com.sentana.job.models.entities.SavedJob;

public class PreferenceMapper {

    public static SavedJobResponse toSavedJobResponse(SavedJob savedJob){
        return SavedJobResponse.builder()
                .id(savedJob.getId())
                .candidateId(savedJob.getCandidateId())
                .jobId(savedJob.getJobId())
                .savedAt(savedJob.getSavedAt())
                .build();
    }
}
