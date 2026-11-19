package com.sentana.job.controllers;

import com.sentana.job.dto.response.UserResponse;
import com.sentana.job.response.success.Response;
import com.sentana.job.services.UserService;
import com.sentana.job.domain.UserRole;
import com.sentana.job.models.requests.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public Response<?> create(
            @RequestBody @Valid UserRequest request
    ) throws IOException {
        userService.create(request);
        return response("user created successfully");
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUser(){
        return ResponseEntity.ok(userService.get());
    }

    @PutMapping(path = "/{id}")
    public Response<?> update(
            @PathVariable Long id,
            @RequestBody @Valid UserRequest request) {
        userService.update(id, request);
        return response("user updated successfully");
    }

    private Response<?> response(String message){
        return Response.builder()
                .status(HttpStatus.OK.value())
                .timestamp(new Date())
                .message(message)
                .build();
    }
}
