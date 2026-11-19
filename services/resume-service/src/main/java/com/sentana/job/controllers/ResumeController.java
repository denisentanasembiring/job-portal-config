package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.PersonalInformationResponse;
import com.sentana.job.dto.response.ResumeResponse;
import com.sentana.job.models.requests.ResumeRequest;
import com.sentana.job.services.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ResumeResponse> createResume(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid ResumeRequest request){
        return ResponseEntity.ok(resumeService.createResume(candidateId, request));
    }

    @GetMapping(path = "/{resumeId}")
    public ResponseEntity<ResumeResponse> getResumeById(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestBody @Valid ResumeRequest request
    ) throws Exception {
        return ResponseEntity.ok(resumeService.getResumeById(resumeId, candidateId));
    }

    @GetMapping(path = "/my-resume")
    public ResponseEntity<List<ResumeResponse>> getMyResume(@RequestHeader("X-User-Id")Long candidateId){
        return ResponseEntity.ok(resumeService.getResumes(candidateId));
    }

    @PutMapping(path = "/{resumeId}/personal-information")
    public ResponseEntity<ResumeResponse> updatePersonalInformation(@PathVariable Long resumeId,
                                                                    @RequestHeader("X-User-Id")Long candidateId,
                                                                    @RequestBody @Valid PersonalInformationResponse request) throws Exception {
        return ResponseEntity.ok(resumeService.updatePersonalInformation(resumeId, candidateId, request));
    }

    @PatchMapping(path = "/{resumeId}/summary")
    public ResponseEntity<ResumeResponse> updateSummary(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestParam String summary
    ) throws Exception {
        return ResponseEntity.ok(resumeService.updateSummary(resumeId, candidateId, summary));
    }

    @PatchMapping(path = "/{resumeId}/set-default")
    public ResponseEntity<ResumeResponse> setDefaultResume(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id")Long candidateId
    ) throws Exception {
        return ResponseEntity.ok(resumeService.setDefaultResume(resumeId, candidateId));
    }


    @DeleteMapping(path = "/{resumeId}")
    public ResponseEntity<ApiResponse> deleteResume(@PathVariable Long resumeId, @RequestHeader("X-User-Id")Long candidateId) throws Exception {
        resumeService.deleteResume(resumeId, candidateId);
        return ResponseEntity.ok(new ApiResponse("resume deleted successfully", true));
    }
}
