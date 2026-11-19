package com.sentana.job.models.requests;

import com.sentana.job.domain.ApplicationStatus;
import lombok.Data;

@Data
public class UpdateApplicationRequest {
    private ApplicationStatus status;
}
