package com.sentana.job.services;

import com.sentana.job.dto.response.WorkExperienceResponse;
import com.sentana.job.models.entities.WorkExperience;
import com.sentana.job.models.requests.WorkExperienceRequest;

import java.util.List;

public interface WorkExperienceService {

    WorkExperienceResponse createExperience(Long resumeId, Long candidateId, WorkExperienceRequest request) throws Exception;

    List<WorkExperienceResponse> getExperiences(Long resumeId);

    WorkExperienceResponse updateExperience(Long resumeId, Long candidateId, Long experienceId, WorkExperienceRequest request) throws Exception;

    void deleteExperience(Long resumeId, Long experienceId, Long candidateId) throws Exception;

    WorkExperience getExperienceEntity(Long experienceId) throws Exception;
}
