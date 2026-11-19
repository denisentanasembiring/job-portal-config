package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.LanguageResponse;
import com.sentana.job.models.requests.LanguageRequest;
import com.sentana.job.services.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/resumes/{resumeId}/languages")
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping
    public ResponseEntity<LanguageResponse> createLanguage(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestBody @Valid LanguageRequest request
            ) throws Exception {
        return ResponseEntity.ok(languageService.createLanguage(resumeId, candidateId, request));
    }

    @GetMapping
    public ResponseEntity<List<LanguageResponse>> getLanguages(@PathVariable Long resumeId){
        return ResponseEntity.ok(languageService.getLanguages(resumeId));
    }

    @PutMapping(path = "/{languageId}")
    public ResponseEntity<LanguageResponse> updateLanguage(
            @PathVariable Long resumeId,
            @PathVariable Long languageId,
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestBody @Valid LanguageRequest request
    ) throws Exception {
        return ResponseEntity.ok(languageService.updateLanguage(languageId, resumeId, candidateId, request));
    }

    @DeleteMapping(path = "/{languageId}")
    public ResponseEntity<ApiResponse> deleteLanguage(
            @PathVariable("resumeId")Long resumeId,
            @PathVariable("languageId") Long languageId,
            @RequestHeader("X-User-Id")Long candidateId
    ) throws Exception {
        languageService.deleteLanguage(languageId, resumeId, candidateId);
        return ResponseEntity.ok(new ApiResponse("language deleted successfully", true));
    }
}
