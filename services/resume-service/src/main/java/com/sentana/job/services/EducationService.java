package com.sentana.job.services;

import com.sentana.job.dto.response.EducationResponse;
import com.sentana.job.models.requests.EducationRequest;

import java.util.List;

public interface EducationService {

    EducationResponse addEducation(Long resumeId, Long candidateId, EducationRequest request) throws Exception;

    List<EducationResponse> getEducations(Long resumeId);

    EducationResponse updateEducation(Long educationId, Long resumeId, Long candidateId, EducationRequest request) throws Exception;

    void deleteEducation(Long educationId, Long resumeId, Long candidateId) throws Exception;
}
