package com.sentana.job.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInformationResponse {
    private String firstname;
    private String lastname;
    private String headline;

    private String email;
    private String phoneNumber;
    private String city;
    private String country;

    private String linkedinUrl;
    private String githubUrl;
    private String portfolioUrl;
    private String websiteUrl;
}
