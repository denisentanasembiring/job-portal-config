package com.sentana.job.services;

import com.sentana.job.dto.response.ApplicationNoteResponse;
import com.sentana.job.models.requests.ApplicationNoteRequest;

import java.util.List;

public interface ApplicationNoteService {

    ApplicationNoteResponse addNote(Long applicationId, Long employerId, ApplicationNoteRequest request) throws Exception;

    List<ApplicationNoteResponse> getNoteByApplication(Long applicationId, Long employerId);

    void deleteNote(Long applicationId, Long noteId, Long employerId) throws Exception;
}
