package com.sentana.job.services.impl;

import com.sentana.job.dto.response.ProjectResponse;
import com.sentana.job.mappers.ResumeMapper;
import com.sentana.job.models.entities.Project;
import com.sentana.job.models.entities.Resume;
import com.sentana.job.models.requests.ProjectRequest;
import com.sentana.job.repositories.ProjectRepository;
import com.sentana.job.services.ProjectService;
import com.sentana.job.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImplement implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ResumeService resumeService;

    @Override
    public ProjectResponse createProject(Long resumeId, Long candidateId, ProjectRequest request) throws Exception {

        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId);
        Project project = Project.builder()
                .resume(resume)
                .title(resume.getTitle())
                .description(request.getDescription())
                .technologies(request.getTechnologies())
                .projectUrl(request.getProjectUrl())
                .sourceUrl(request.getSourceUrl())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isOnGoing(Boolean.TRUE.equals(request.getIsOnGoing()))
                .displayOrder(request.getDisplayOrder()!=null ? request.getDisplayOrder():0)
                .build();
        return ResumeMapper.toProjectResponse(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponse> getProjects(Long resumeId) {
        return projectRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(ResumeMapper::toProjectResponse).toList();
    }

    @Override
    public ProjectResponse updateProject(Long projectId, Long resumeId, Long candidateId, ProjectRequest request) throws Exception {
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new Exception("project with id " + projectId + "not found")
        );

        assertOwner(project.getResume(), candidateId);
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        if (request.getTechnologies()!=null) project.setTechnologies(request.getTechnologies());
        project.setProjectUrl(request.getProjectUrl());
        project.setSourceUrl(request.getSourceUrl());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setIsOnGoing(Boolean.TRUE.equals(request.getIsOnGoing()));
        if (request.getDisplayOrder()!=null) request.setDisplayOrder(request.getDisplayOrder());
        return ResumeMapper.toProjectResponse(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long projectId, Long resumId, Long candidateId) throws Exception {
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new Exception("project with id " + projectId + "not found")
        );
        assertOwner(project.getResume(), candidateId);
        projectRepository.delete(project);
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)){
            throw new Exception("resume with id " +resume.getId()+ "not found");
        }
    }
}
