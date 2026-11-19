package com.sentana.job.models.requests;

import com.sentana.job.domain.CompanySize;
import com.sentana.job.domain.CompanyType;
import com.sentana.job.domain.IndustryType;
import com.sentana.job.models.responses.SocialLinkResponse;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {

    @NotBlank(message = "company name is required")
    private String companyName;

    private String tagline;
    private String description;
    private String logoUrl;
    private String coverImageUrl;

    @Pattern(regexp = "^(https?://).*", message = "website must be valid url")
    private String website;

    @Email(message = "company email must be valid")
    private String email;

    private String phone;

    @Min(value = 1800, message = "company year seems to old")
    @Max(value = 2100, message = "founded year is in valid")
    private Integer foundedYear;

    @NotNull(message = "company size is require")
    private CompanySize companySize;

    @NotNull(message = "company type is required")
    private CompanyType companyType;

    @NotNull(message = "industry type is required")
    private IndustryType industryType;

    @NotNull(message = "owner id can't be null")
    private Long ownerId;

    /**
     * Official Company Registration / CIN number for varification
     */
    @NotNull(message = "registration number is mandatory")
    private String registrationNumber;

    private List<SocialLinkResponse> socialLinks;
}
