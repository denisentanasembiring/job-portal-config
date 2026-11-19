package com.sentana.job.response.success;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paging {
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
