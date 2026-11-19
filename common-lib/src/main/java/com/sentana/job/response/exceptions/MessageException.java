package com.sentana.job.response.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MessageException<T> {
    private int status;
    private String timestamp;
    private String message;
    private String detail;
    private String path;
    private T data;
}
