package com.sentana.job.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentana.job.auth.keys.SecretKey;
import com.sentana.job.models.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
public class CustomAuthentication extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final String secretKey = SecretKey.getKey();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        log.info("email is {}", email);
        log.info("password is {}", password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication) throws IOException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User userAccount = userPrincipal.getUser();
        AuthenticationToken authToken = new AuthenticationToken();
        Date date = new Date(System.currentTimeMillis() + 60 * 28800 * 1000);
        Algorithm alg = Algorithm.HMAC512(secretKey.getBytes());

        String accessToken = JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuer(request.getRequestURL().toString())
                .withExpiresAt(date)
                .withClaim("role", userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(alg);

        String refreshToken = JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuer(request.getRequestURL().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + 65 * 28800 * 1000))
                .sign(alg);

        DateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");
        authToken.setAccessToken(accessToken);
        authToken.setRefreshToken(refreshToken);
        authToken.setEnabled(userAccount.isEnabled());
        authToken.setAccountNonExpired(userAccount.isAccountNonExpired());
        authToken.setAccountNonLocked(userAccount.isAccountNonLocked());
        authToken.setCredentialsNonExpired(userAccount.isCredentialsNonExpired());
        authToken.setExpiredTimeMillis(date.getTime());
        authToken.setExpiredDateTime(formatter.format(date.getTime()));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), authToken);
    }

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class AuthenticationToken {
    private String accessToken;
    private String refreshToken;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private long expiredTimeMillis;
    private String expiredDateTime;
}
