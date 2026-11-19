package com.sentana.job.models.embed;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class JobLocation {

    private String address;
    private String city;
    private String country;
    private String state;
    private String zipCode;
}
