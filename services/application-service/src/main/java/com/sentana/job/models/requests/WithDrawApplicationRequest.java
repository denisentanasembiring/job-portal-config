package com.sentana.job.models.requests;

import lombok.*;

@Getter
@Setter
@Builder@NoArgsConstructor
@AllArgsConstructor
public class WithDrawApplicationRequest {

    private String reason;
}
