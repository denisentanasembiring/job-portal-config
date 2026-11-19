package com.sentana.job.mappers;

import com.sentana.job.domain.ApplicationStatus;
import com.sentana.job.dto.response.*;
import com.sentana.job.models.entities.Application;
import com.sentana.job.models.entities.ApplicationNote;
import com.sentana.job.models.requests.ApplicationRequest;

import java.util.List;

public class ApplicationMapper {
    public static Application toEntity(ApplicationRequest request, Long candidateId, Long companyId, Long employerId){
        if (request==null) return null;
        return Application.builder()
                .candidateId(candidateId)
                .jobId(request.getJobId())
                .companyId(companyId)
                .employerId(employerId)
                .resumeId(request.getResumeId())
                .coverLetter(request.getCoverLetter())
                .expectedSalary(request.getExpectedSalary())
                .availableFrom(request.getAvailableFrom())
                .applicationStatus(ApplicationStatus.PENDING)
                .build();
    }

    public static ApplicationResponse toApplicationResponse(Application application,
                                                            List<ApplicationNote> notes,
                                                            JobResponse job,
                                                            CompanyResponse company,
                                                            UserResponse candidate
//                                                            ApplicationScreening screening
    ){
        return ApplicationResponse.builder()
                .id(application.getId())
                .candidate(candidate)
                .employerId(application.getEmployerId())
                .job(job)
                .company(company)
                .status(application.getApplicationStatus())
                .resumeId(application.getResumeId())
                .coverLetter(application.getCoverLetter())
                .expectedSalary(application.getExpectedSalary())
                .availableFrom(application.getAvailableFrom())
                .isStarred(application.getIsStarred())
                .notes(notes.stream().map(ApplicationMapper::toApplicationNoteResponse).toList())
                .withDrawnAt(application.getWithDrawnAt())
                .withDrawnReason(application.getWithDrawnReason())
                .appliedAt(application.getAppliedAt())
                .updatedAt(application.getUpdatedAt())
                //.screening(toScreeningResponse()screening)
                .build();
    }

    public static ApplicationNoteResponse toApplicationNoteResponse(ApplicationNote applicationNote){
        return ApplicationNoteResponse.builder()
                .id(applicationNote.getId())
                .addByUserId(applicationNote.getAddedByUserId())
                .content(applicationNote.getContent())
                .createdAt(applicationNote.getCreatedAt())
                .build();
    }
}
