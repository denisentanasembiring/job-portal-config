package com.sentana.job.mapper;

import com.sentana.job.dto.response.CompanyResponse;
import com.sentana.job.dto.response.SocialLinkResponse;
import com.sentana.job.models.entities.Company;
import com.sentana.job.models.entities.SocialLink;

import java.util.Collections;
import java.util.List;

public class CompanyMapper {

    private static SocialLinkResponse toSocialLinkResponse(SocialLink socialLinks){
        return SocialLinkResponse.builder()
                .platform(socialLinks.getPlatform())
                .url(socialLinks.getUrl())
                .build();
    }

    public static CompanyResponse toResponse(Company company) {
        List<SocialLinkResponse> socialLinkResponses = company
                .getSocialLinks()==null?Collections
                .emptyList():company
                .getSocialLinks().stream()
                .map(CompanyMapper::toSocialLinkResponse)
                .toList();

        return CompanyResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .slug(company.getSlug())
                .tagline(company.getTagline())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .companyStatus(company.getCompanyStatus())
                .active(company.getActive())
                .owerId(company.getId())
                .socialLinks(socialLinkResponses)
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }
}
