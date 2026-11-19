package com.sentana.job.models.embed;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SalaryRange {

    private BigDecimal minSalary;
    private BigDecimal maxSalary;
}
