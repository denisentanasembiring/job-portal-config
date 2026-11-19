package com.sentana.job.services;

import com.sentana.job.dto.response.JobTagResponse;
import com.sentana.job.models.entities.JobTag;
import com.sentana.job.models.requests.JobTagRequest;

import java.util.List;
import java.util.Set;

public interface JobTagService {

    JobTagResponse createTag(JobTagRequest request) throws Exception;

    List<JobTagResponse> getAllJobTags();

    JobTagResponse getTagById(Long id) throws Exception;

    JobTagResponse updateTag(Long id, JobTagRequest request) throws Exception;

    void deleteTag(Long id) throws Exception;

    JobTag getTagEntityById(Long id) throws Exception;

    Set<JobTag> getTagsById(Set<Long> id) throws Exception;
}
