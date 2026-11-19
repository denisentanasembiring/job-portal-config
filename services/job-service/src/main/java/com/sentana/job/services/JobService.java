package com.sentana.job.services;

import com.sentana.job.models.requests.JobRequest;
import com.sentana.job.models.requests.JobSearchRequest;
import com.sentana.job.dto.response.JobResponse;

import java.util.List;

public interface JobService {

    JobResponse createJob(Long employerId, JobRequest request) throws Exception;
    JobResponse getJobById(Long id) throws Exception;
    List<JobResponse> getJobs(JobSearchRequest request);
    List<JobResponse> getJobsByCompany(Long companyId);
    JobResponse updateJob(Long jobId, Long employerId, JobRequest request) throws Exception;
    JobResponse publishJob(Long jobId, Long employerId) throws Exception;
    JobResponse closeJob(Long jobId, Long employerId) throws Exception;
    void deleteJob(Long jobId, Long employerId) throws Exception;
    List<JobResponse> getAllJobsAmin();


}
