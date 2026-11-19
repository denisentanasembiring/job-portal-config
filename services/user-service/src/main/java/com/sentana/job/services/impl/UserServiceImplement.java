package com.sentana.job.services.impl;

import com.sentana.job.auth.UserPrincipal;
import com.sentana.job.domain.UserRole;
import com.sentana.job.dto.response.UserResponse;
import com.sentana.job.models.entities.Role;
import com.sentana.job.models.entities.User;
import com.sentana.job.models.requests.UserRequest;
import com.sentana.job.repositories.RoleRepository;
import com.sentana.job.repositories.UserRepository;
import com.sentana.job.response.exceptions.ResourceNotFoundException;
import com.sentana.job.services.MailService;
import com.sentana.job.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.module.ResolutionException;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String create(UserRequest request) throws IOException {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setEnabled(false);
        user.setAccountNonExpired(false);
        user.setAccountNonLocked(false);
        user.setCredentialsNonExpired(false);
        user.setRole(request.getRole());
        userRepository.save(user);
        return "user created successfully";
    }

    @Override
    public List<UserResponse> get() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::response).toList();
    }

    @Override
    public Optional<UserResponse> get(Long id) {
        Optional<User> user = Optional.of(userRepository.findById(id).orElseThrow(
                ()-> new ResolutionException(MessageFormat.format("id {0} not found", id)))
        );
        return user.map(this::response);
    }

    @Override
    public String update(Long id, UserRequest request) {
        return "";
    }

    @Override
    public String delete(Long id) {
        return "";
    }

    @Override
    public String destroy(Long id) {
        return "";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.of(userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("username not found")));
        User user = userOptional.get();
        Role role = user.getRole();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return new UserPrincipal(
                user.getUsername(),
                user.getPassword(),
                user,
                authorities
        );
    }

    private UserResponse response(User user){
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonExpired())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .role(UserRole.valueOf(user.getRole().getName()))
                .build();
    }
}
