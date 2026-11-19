package com.sentana.job.controllers;


import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.EducationResponse;
import com.sentana.job.models.requests.EducationRequest;
import com.sentana.job.services.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/resumes/{resumeId}/educations")
public class EducationController {

    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<EducationResponse> createEducation(
            @PathVariable("resumeId")Long resumeId,
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestBody @Valid EducationRequest request) throws Exception {
        return ResponseEntity.ok(educationService.addEducation(resumeId, candidateId, request));
    }

    @GetMapping
    public ResponseEntity<List<EducationResponse>> getEducations(@PathVariable Long resumeId){
        return ResponseEntity.ok(educationService.getEducations(resumeId));
    }

    @PutMapping(path = "/{educationId}")
    public ResponseEntity<EducationResponse> updateEducation(
            @PathVariable("resumeId")Long resumeId,
            @PathVariable("educationId")Long educationId,
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestBody @Valid EducationRequest request
    ) throws Exception {
        return ResponseEntity.ok(educationService.updateEducation(educationId, resumeId, candidateId, request));
    }

    @DeleteMapping(path = "/{educationId}")
    public  ResponseEntity<ApiResponse> deleteEducation(
            @PathVariable("resumeId")Long resumeId,
            @PathVariable("educationId")Long educationId,
            @RequestHeader("X-User-Id")Long candidateId
    ) throws Exception {
        educationService.deleteEducation(educationId, resumeId, candidateId);
        return ResponseEntity.ok(new ApiResponse("education deleted successfully", true));
    }
}
