package com.sentana.job.controllers;

import com.sentana.job.dto.response.ApiResponse;
import com.sentana.job.dto.response.ApplicationNoteResponse;
import com.sentana.job.models.requests.ApplicationNoteRequest;
import com.sentana.job.services.ApplicationNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/applications/{applicationId}/notes")
public class ApplicationNoteController {

    private final ApplicationNoteService noteService;

    @PostMapping
    public ResponseEntity<ApplicationNoteResponse> addNote(
            @PathVariable Long applicationId,
            @RequestHeader("X-User-Id")Long employerId,
            @RequestBody @Valid ApplicationNoteRequest request
            ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.addNote(applicationId, employerId, request));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationNoteResponse>> getNotes(
            @PathVariable Long applicationId,
            @RequestHeader("X-User-Id")Long employerId
    ){
        return ResponseEntity.ok(noteService.getNoteByApplication(applicationId, employerId));
    }

    @DeleteMapping(path = "/{noteId}")
    public ResponseEntity<ApiResponse> deleteNote(
            @PathVariable Long applicationId,
            @PathVariable Long noteId,
            @RequestHeader("X-User-Id")Long employerId
    ) throws Exception {
        noteService.deleteNote(applicationId, noteId, employerId);
        return ResponseEntity.ok(new ApiResponse("note deleted successfully", true));
    }
}
