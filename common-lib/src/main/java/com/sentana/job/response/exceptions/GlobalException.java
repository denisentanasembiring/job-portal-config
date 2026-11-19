package com.sentana.job.response.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GlobalException extends ResponseEntityExceptionHandler {
    SimpleDateFormat df = new SimpleDateFormat("EEEE dd MMM, yyyy HH:mm:ss z");
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageException.<String>builder()
                        .status(status.value())
                        .timestamp(df.format(new Date()))
                        .message(ex.getMessage())
                        .detail(ex.getDetailMessageCode())
                        .path(request.getDescription(true))
                        .build());
    }
}
