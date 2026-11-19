package com.sentana.job.mappers;

import com.sentana.job.dto.response.UserResponse;
import com.sentana.job.models.entities.User;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()

                .build();
    }
}
