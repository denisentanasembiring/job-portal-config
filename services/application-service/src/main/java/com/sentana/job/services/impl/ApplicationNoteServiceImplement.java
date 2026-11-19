package com.sentana.job.services.impl;

import com.sentana.job.dto.response.ApplicationNoteResponse;
import com.sentana.job.mappers.ApplicationMapper;
import com.sentana.job.models.entities.Application;
import com.sentana.job.models.entities.ApplicationNote;
import com.sentana.job.models.requests.ApplicationNoteRequest;
import com.sentana.job.repositories.ApplicationNoteRepository;
import com.sentana.job.services.ApplicationNoteService;
import com.sentana.job.services.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationNoteServiceImplement implements ApplicationNoteService {

    private final ApplicationNoteRepository applicationNoteRepository;
    private final ApplicationService applicationService;

    @Override
    public ApplicationNoteResponse addNote(Long applicationId, Long employerId, ApplicationNoteRequest request) throws Exception {
        Application application = applicationService.getApplicationEntity(applicationId);
        assertEmployer(application, employerId);
        ApplicationNote applicationNote= ApplicationNote.builder()
                .application(application)
                .addedByUserId(employerId)
                .content(request.getContent())
                .build();
        return ApplicationMapper.toApplicationNoteResponse(applicationNoteRepository.save(applicationNote));
    }

    @Override
    public List<ApplicationNoteResponse> getNoteByApplication(Long applicationId, Long employerId) {
        return applicationNoteRepository.findByApplicationId(applicationId)
                .stream().map(ApplicationMapper::toApplicationNoteResponse).toList();
    }

    @Override
    public void deleteNote(Long applicationId, Long noteId, Long employerId) throws Exception {
        Application application = applicationService.getApplicationEntity(applicationId);
        assertEmployer(application, employerId);
        ApplicationNote note = applicationNoteRepository.findById(noteId).orElseThrow(
                ()->new Exception("note does not belong to application")
        );
        applicationNoteRepository.delete(note);
    }

    private void assertEmployer(Application application, Long employerId) throws Exception {
        if (!application.getEmployerId().equals(employerId)){
            throw new Exception("you're not the employer for this application");
        }
    }
}
