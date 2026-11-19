package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.ProjectResponse;
import com.sentana.job.models.requests.ProjectRequest;
import com.sentana.job.services.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/resumes/{resumeId}/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @PathVariable("resumeId")Long resumeId,
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestBody @Valid ProjectRequest request) throws Exception {
        return ResponseEntity.ok(projectService.createProject(resumeId, candidateId, request));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(@PathVariable("resumeId")Long resumeId){
        return ResponseEntity.ok(projectService.getProjects(resumeId));
    }

    @PutMapping(path = "/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable("resumeId")Long resumeId,
            @PathVariable("projectId") Long projectId,
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestBody @Valid ProjectRequest request
    ) throws Exception {
        return ResponseEntity.ok(projectService.updateProject(projectId, resumeId, candidateId, request));
    }

    @DeleteMapping(path = "/{projectId}")
    public ResponseEntity<ApiResponse> deleteProject(
            @PathVariable("resumeId")Long resumeId,
            @PathVariable("projectId") Long projectId,
            @RequestHeader("X-User-Id")Long candidateId
    ) throws Exception {
        projectService.deleteProject(resumeId, projectId, candidateId);
        return ResponseEntity.ok(new ApiResponse("project deleted successfully", true));
    }
}
