package com.sentana.job.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCategoryRequest {

    @NotBlank(message = "category name is require")
    private String name;

    @Size(max = 500, message = "description must not exceed 500 characters")
    private String description;

    private String iconUrl;

    private Long parentId;
}
