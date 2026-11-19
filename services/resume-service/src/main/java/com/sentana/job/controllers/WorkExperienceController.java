package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.WorkExperienceResponse;
import com.sentana.job.models.entities.WorkExperience;
import com.sentana.job.models.requests.WorkExperienceRequest;
import com.sentana.job.services.WorkExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/resume/{resumeId}/work-experiences")
public class WorkExperienceController {

    private final WorkExperienceService experienceService;

    @PostMapping
    public ResponseEntity<WorkExperienceResponse> createExperience(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestBody @Valid WorkExperienceRequest request) throws Exception {
        return ResponseEntity.ok(experienceService.createExperience(resumeId, candidateId, request));
    }

    @GetMapping
    public ResponseEntity<List<WorkExperienceResponse>> getExperiences(@PathVariable Long resumeId){
        return ResponseEntity.ok(experienceService.getExperiences(resumeId));
    }

    @GetMapping(path = "/{experienceId}")
    public ResponseEntity<WorkExperienceResponse> getExperienceById(@PathVariable Long resumeId,
                                                                    @PathVariable Long experienceId){
        return null;
    }

    @PutMapping
    public ResponseEntity<WorkExperienceResponse> updateExperience(
            @PathVariable Long resumeId,
            @PathVariable Long experienceId,
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestBody @Valid WorkExperienceRequest request
    ) throws Exception {
        return ResponseEntity.ok(experienceService.updateExperience(resumeId, candidateId, experienceId, request));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteExperience(
            @PathVariable Long resumeId,
            @PathVariable Long experienceId,
            @RequestHeader("X-User-Id") Long candidateId
    ) throws Exception {
        experienceService.deleteExperience(resumeId, experienceId, candidateId);
        return ResponseEntity.ok(new ApiResponse("experience deleted successfully",true));
    }
}
