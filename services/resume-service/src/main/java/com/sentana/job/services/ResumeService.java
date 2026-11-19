package com.sentana.job.services;

import com.sentana.job.dto.response.PersonalInformationResponse;
import com.sentana.job.dto.response.ResumeResponse;
import com.sentana.job.models.entities.Resume;
import com.sentana.job.models.requests.ResumeRequest;

import java.util.List;

public interface ResumeService {
    ResumeResponse createResume(Long candidateId, ResumeRequest request);

    List<ResumeResponse> getResumes(Long candidateId);

    ResumeResponse getResumeById(Long id, Long candidateId) throws Exception;

    ResumeResponse updatePersonalInformation(Long id, Long candidateId, PersonalInformationResponse request) throws Exception;

    ResumeResponse updateSummary(Long id, Long candidateId, String summary) throws Exception;

    ResumeResponse setDefaultResume(Long id, Long candidateId) throws Exception;

    void deleteResume(Long id, Long candidateId) throws Exception;

    Resume getResumeEntity(Long id) throws Exception;
}
