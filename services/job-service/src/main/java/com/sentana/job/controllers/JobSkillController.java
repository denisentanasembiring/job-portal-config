package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.JobSkillResponse;
import com.sentana.job.models.requests.JobSkillRequest;
import com.sentana.job.services.JobSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/job-skills")
public class JobSkillController {
    private final JobSkillService jobSkillService;

    @PostMapping
    public ResponseEntity<JobSkillResponse> createJobSkill(@RequestBody @Valid JobSkillRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobSkillService.createJobSkill(request));
    }

    @GetMapping
    public ResponseEntity<List<JobSkillResponse>> getAllSkills(){
        return ResponseEntity.ok(jobSkillService.getAllSkills());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<JobSkillResponse> getSkillById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(jobSkillService.getAllSkillById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<JobSkillResponse> updateJobSkill(@PathVariable Long id, @RequestBody @Valid JobSkillRequest request) throws Exception {
        return ResponseEntity.ok(jobSkillService.updateSkill(id, request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteSkill(@PathVariable Long id) throws Exception {
        jobSkillService.deleteJobSkill(id);
        return ResponseEntity.ok(new ApiResponse("skill successfully deleted", true));
    }
}
