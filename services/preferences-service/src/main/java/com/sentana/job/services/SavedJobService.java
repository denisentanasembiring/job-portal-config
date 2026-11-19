package com.sentana.job.services;

import com.sentana.job.dto.response.SavedJobResponse;
import com.sentana.job.models.requests.SavedJobRequest;

import java.util.List;

public interface SavedJobService {
    SavedJobResponse saveJob(Long candidateId, SavedJobRequest request) throws Exception;
    void unsaveJob(Long candidateId, Long savedJobId) throws Exception;
    List<SavedJobResponse> getSavedJob(Long candidateId);
    boolean isSaved(Long candidateId, Long jobId);
}
