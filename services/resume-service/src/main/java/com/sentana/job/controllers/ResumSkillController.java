package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.ResumeSkillResponse;
import com.sentana.job.models.requests.ResumeSkillRequest;
import com.sentana.job.services.ResumeSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/resumes/{resumeId}/skills")
public class ResumSkillController {

    private final ResumeSkillService resumeSkillService;

    @PostMapping
    public ResponseEntity<ResumeSkillResponse> createResumSkill(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid ResumeSkillRequest request
            ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(resumeSkillService.createResumSkill(resumeId, candidateId, request));
    }

    @GetMapping
    public ResponseEntity<List<ResumeSkillResponse>> getSkills(@PathVariable Long resumeId){
        return ResponseEntity.ok(resumeSkillService.getSkills(resumeId));
    }

    @PutMapping(path = "/{skillId}")
    public ResponseEntity<ResumeSkillResponse> updateResumSkill(
            @PathVariable Long resumeId,
            @PathVariable Long skillId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid ResumeSkillRequest request
    ) throws Exception {
        return ResponseEntity.ok(resumeSkillService.updateResumeSkill(skillId, resumeId, candidateId, request));
    }

    public ResponseEntity<ApiResponse> createResumSkill(
            @PathVariable Long resumeId,
            @PathVariable Long skillId,
            @RequestHeader("X-User-Id") Long candidateId
    ) throws Exception {
        resumeSkillService.deleteResumeSkill(skillId, resumeId, candidateId);
        return ResponseEntity.ok(new ApiResponse("skill deleted successfully", true));
    }
}
