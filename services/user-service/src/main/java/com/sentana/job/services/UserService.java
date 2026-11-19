package com.sentana.job.services;

import com.sentana.job.dto.response.UserResponse;
import com.sentana.job.models.requests.UserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    String create(UserRequest request) throws IOException;

    List<UserResponse> get();

    Optional<UserResponse> get(Long id);

    String update(Long id, UserRequest request);

    String delete(Long id);

    String destroy(Long id);
}
