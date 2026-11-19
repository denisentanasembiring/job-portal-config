package com.sentana.job.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private List<String> technologies;
    private String projectUrl;
    private String sourceUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isOnGoing;
    private Integer displayOrder;

}
