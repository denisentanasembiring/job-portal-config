package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.JobTagResponse;
import com.sentana.job.models.requests.JobTagRequest;
import com.sentana.job.services.JobTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/job-tags")
public class JobTagController {
    private final JobTagService jobTagService;

    @PostMapping
    public ResponseEntity<JobTagResponse> createTag (@RequestBody @Valid JobTagRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobTagService.createTag(request));
    }

    @GetMapping
    public ResponseEntity<List<JobTagResponse>> getAllTags(){
        return ResponseEntity.ok(jobTagService.getAllJobTags());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<JobTagResponse> getTagById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(jobTagService.getTagById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<JobTagResponse> updateTag(@PathVariable Long id, @RequestBody @Valid JobTagRequest request ) throws Exception {
        return ResponseEntity.ok(jobTagService.updateTag(id, request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteTag(@PathVariable Long id) throws Exception {
        jobTagService.deleteTag(id);
        return ResponseEntity.ok(new ApiResponse("tag successfully deleted", true));
    }
}
