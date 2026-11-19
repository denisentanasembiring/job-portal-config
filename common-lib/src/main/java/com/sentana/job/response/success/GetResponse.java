package com.sentana.job.response.success;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetResponse<T> {
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss")
    private Date timestamp;
    private String message;
    private T data;
}
