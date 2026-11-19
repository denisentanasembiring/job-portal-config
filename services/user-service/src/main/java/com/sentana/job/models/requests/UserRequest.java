package com.sentana.job.models.requests;

import com.sentana.job.models.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {


    @Email(message = "email should be valid")
    @NotBlank(message = "username is mandatory")
    private String username;

    @NotBlank(message = "full name is mandatory")
    private String fullName;

    @NotBlank(message = "password is mandatory")
    private String password;

    private String phone;

    private Role role;
}
