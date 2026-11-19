package com.sentana.job.dto.response;

import com.sentana.job.domain.ApplicationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;
    private UserResponse candidate;
    private Long employerId;
    private JobResponse job;
    private CompanyResponse company;
    private ApplicationStatus status;
    private Long resumeId;
    private String coverLetter;
    private BigDecimal expectedSalary;
    private LocalDate availableFrom;
    private Boolean isStarred;
    private List<ApplicationNoteResponse> notes;
    private LocalDateTime withDrawnAt;
    private String withDrawnReason;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

    // private ApplicationScreeningResponse screening;

}
