package com.sentana.job.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationNoteResponse {

    private Long id;
    private Long addByUserId;
    private String content;
    private LocalDateTime createdAt;
}
