package com.sentana.job.utils;

import jakarta.validation.constraints.NotBlank;

public class GenerateUniqueSlug {
    public static String execute(@NotBlank(message = "category name is require") String name){
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim().replaceAll("[\\s-]", "-");
    }
}
