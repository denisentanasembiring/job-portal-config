package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.models.requests.JobRequest;
import com.sentana.job.models.requests.JobSearchRequest;
import com.sentana.job.dto.response.JobResponse;
import com.sentana.job.services.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@RequestHeader("X-User-Id") Long employerId, @RequestBody @Valid JobRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(employerId, request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getJobs(@ModelAttribute JobSearchRequest request){
        return ResponseEntity.ok(jobService.getJobs(request));
    }

    @GetMapping(path = "/company/{companyId}")
    public ResponseEntity<List<JobResponse>> getJobByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(jobService.getJobsByCompany(companyId));
    }

    @GetMapping(path = "/admin")
    public ResponseEntity<List<JobResponse>> getJobAdmin() {
        return ResponseEntity.ok(jobService.getAllJobsAmin());
    }

    @PutMapping
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long id, @RequestHeader("X-User-Id") Long employerId, @RequestBody JobRequest request) throws Exception {
        return ResponseEntity.ok(jobService.updateJob(id, employerId, request));
    }

    @PatchMapping(path = "/{id}/publish")
    public ResponseEntity<JobResponse> publishedJob(@PathVariable Long id, @RequestHeader("X-User-Id") Long employerId) throws Exception {
        return ResponseEntity.ok(jobService.publishJob(id, employerId));
    }

    @PatchMapping(path = "/{id}/close")
    public ResponseEntity<JobResponse> closedJob(@PathVariable Long id, @RequestHeader("X-User-Id") Long employerId) throws Exception {
        return ResponseEntity.ok(jobService.publishJob(id, employerId));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long id, @RequestHeader("X-User-Id") Long employerId) throws Exception {
        jobService.deleteJob(id, employerId);
        return ResponseEntity.ok(new ApiResponse("job deleted successfully", true));
    }
}
