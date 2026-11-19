package com.sentana.job;

import com.sentana.job.domain.UserRole;
import com.sentana.job.models.entities.Role;
import com.sentana.job.models.entities.User;
import com.sentana.job.repositories.RoleRepository;
import com.sentana.job.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class UserService {
    public static void main(String[] args){
        SpringApplication.run(UserService.class, args);
    }

}

@Configuration
@RequiredArgsConstructor
class StartRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;
    List<Role> roles = new ArrayList<>();

    @Override
    public void run(String @NonNull ... args) throws Exception {
        roles = roleRepository.findAll();
        if(roles.isEmpty()){
            roleRepository.save(new Role(null, UserRole.ROLE_ADMIN.toString()));
            roleRepository.save(new Role(null, UserRole.ROLE_EMPLOYER.toString()));
            roleRepository.save(new Role(null, UserRole.ROLE_JOB_SEEKER.toString()));
        }
    }
}