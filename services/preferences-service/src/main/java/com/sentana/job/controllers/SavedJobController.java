package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.SavedJobResponse;
import com.sentana.job.models.requests.SavedJobRequest;
import com.sentana.job.services.SavedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/preferences/saved-jobs")
public class SavedJobController {

    private final SavedJobService savedJobService;

    @PostMapping
    public ResponseEntity<SavedJobResponse> saveJob(
            @RequestHeader("X-User-Id")Long candidateId,
            @RequestBody SavedJobRequest request) throws Exception {
        return ResponseEntity.ok(savedJobService.saveJob(candidateId, request));
    }

    @GetMapping
    public ResponseEntity<List<SavedJobResponse>> getMySavedJobs(@RequestHeader("X-User-Id")Long candidateId){
        return ResponseEntity.ok(savedJobService.getSavedJob(candidateId));
    }

    @GetMapping(path = "/check")
    public ResponseEntity<Boolean> isSaved(@RequestHeader("X-User-Id")Long candidateId,
                                           @RequestParam Long jobId){
        return ResponseEntity.ok(savedJobService.isSaved(candidateId, jobId));
    }

    @DeleteMapping(path = "/{savedJobId}")
    public ResponseEntity<ApiResponse> unsaveJob(@PathVariable Long saveJobId,
                                                 @RequestHeader("X-User-Id")Long candidateId) throws Exception {
        savedJobService.unsaveJob(candidateId, saveJobId);
        return ResponseEntity.ok(new ApiResponse("job removed from saved list", true));
    }
}
