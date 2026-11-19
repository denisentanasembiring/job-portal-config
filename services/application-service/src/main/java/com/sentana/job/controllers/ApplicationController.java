package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.ApplicationResponse;
import com.sentana.job.models.requests.ApplicationRequest;
import com.sentana.job.models.requests.CompanyApplicationFilterRequest;
import com.sentana.job.models.requests.UpdateApplicationRequest;
import com.sentana.job.models.requests.WithDrawApplicationRequest;
import com.sentana.job.services.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestHeader("X-User-Id")Long candidateId,
                                                                 @RequestBody @Valid ApplicationRequest request) throws Exception {
        return ResponseEntity.ok(applicationService.createApplication(candidateId, request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApplicationResponse> getApplication(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @GetMapping(path = "/my")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(@RequestHeader("X-User-Id")Long candidateId){
        return ResponseEntity.ok(applicationService.getMyApplications(candidateId));
    }

    @GetMapping(path = "/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationForJob(@PathVariable Long jobId){
        return ResponseEntity.ok(applicationService.getApplicationForJob(jobId));
    }

    @GetMapping(path = "/company")
    public ResponseEntity<List<ApplicationResponse>> getApplicationForCompany(@RequestHeader("X-User-Id") Long userId,
                                                                              @ModelAttribute CompanyApplicationFilterRequest filterRequest){
        return ResponseEntity.ok(applicationService.getApplicationForCompany(userId, filterRequest));
    }

    @PatchMapping(path = "/{id}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(@PathVariable Long id,
                                                            @RequestHeader("X-User-Id")Long employerId,
                                                            @RequestBody @Valid UpdateApplicationRequest request) throws Exception {
        return ResponseEntity.ok(applicationService.updateStatus(id, employerId, request.getStatus()));
    }

    @PatchMapping(path = "/{id}/withdraw")
    public ResponseEntity<ApplicationResponse> withDraw(@PathVariable Long id,
                                                        @RequestHeader("X-User-Id")Long candidateId,
                                                        @RequestBody WithDrawApplicationRequest request) throws Exception {
        return ResponseEntity.ok(applicationService.withdraw(id, candidateId, request));
    }

    @PatchMapping(path = "/{id}/star")
    public ResponseEntity<ApplicationResponse> toggleStar(@PathVariable Long id,
                                                        @RequestHeader("X-User-Id")Long employerId) throws Exception {
        return ResponseEntity.ok(applicationService.toggleStar(id, employerId));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteApplication(@PathVariable Long id, @RequestHeader("X-User-Id")Long candidateId) throws Exception {
        applicationService.deleteApplication(id, candidateId);
        return ResponseEntity.ok(new ApiResponse("application deleted successfully", true));
    }
}
