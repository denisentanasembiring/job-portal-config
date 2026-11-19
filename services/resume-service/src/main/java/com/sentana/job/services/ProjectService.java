package com.sentana.job.services;

import com.sentana.job.dto.response.ProjectResponse;
import com.sentana.job.models.requests.ProjectRequest;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(Long resumeId, Long candidateId, ProjectRequest request) throws Exception;

    List<ProjectResponse> getProjects(Long resumeId);

    ProjectResponse updateProject(Long projectId, Long resumeId, Long candidateId, ProjectRequest request) throws Exception;

    void deleteProject(Long projectId, Long resumId, Long candidateId) throws Exception;
}
