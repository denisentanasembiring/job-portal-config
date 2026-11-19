package com.sentana.job.services;

import com.sentana.job.domain.ApplicationStatus;
import com.sentana.job.dto.response.ApplicationResponse;
import com.sentana.job.models.entities.Application;
import com.sentana.job.models.requests.ApplicationRequest;
import com.sentana.job.models.requests.CompanyApplicationFilterRequest;
import com.sentana.job.models.requests.WithDrawApplicationRequest;

import java.util.List;

public interface ApplicationService {
    ApplicationResponse createApplication(Long candidateId, ApplicationRequest request) throws Exception;

    ApplicationResponse getApplicationById(Long applicationId) throws Exception;

    List<ApplicationResponse> getMyApplications(Long candidateId);

    List<ApplicationResponse> getApplicationForJob(Long jobId);

    List<ApplicationResponse> getApplicationForCompany(Long userId, CompanyApplicationFilterRequest filterRequest);

    ApplicationResponse updateStatus(Long applicationId, Long employerId, ApplicationStatus status) throws Exception;

    ApplicationResponse withdraw(Long applicationId, Long candidateId, WithDrawApplicationRequest request) throws Exception;

    ApplicationResponse toggleStar(Long applicationId, Long employerId) throws Exception;

    void deleteApplication(Long applicationId, Long candidateId) throws Exception;

    Application getApplicationEntity(Long id) throws Exception;


}
