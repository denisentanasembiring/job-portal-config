package com.sentana.job.models.responses;

import com.sentana.job.dto.response.ApiResponse;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private String title;
    private String message;
    private ApiResponse user;
}
