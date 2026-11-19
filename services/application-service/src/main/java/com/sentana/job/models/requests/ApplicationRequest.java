package com.sentana.job.models.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {

    @NotNull(message = "job id is required")
    private Long jobId;

    @NotNull(message = "resume id is required")
    private Long resumeId;

    @Size(max = 3000, message = "cover letter must be exceed 3000 characters")
    private String coverLetter;

    @DecimalMin(value = "0.0", message = "expected salary must not be negative")
    private BigDecimal expectedSalary;

    private LocalDate availableFrom;
}
