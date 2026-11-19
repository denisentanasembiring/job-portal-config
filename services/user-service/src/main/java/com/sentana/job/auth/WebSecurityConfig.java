package com.sentana.job.auth;

import com.sentana.job.domain.UserRole;
import com.sentana.job.url.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authManager = authManagerBuilder.build();
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(se -> se.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(Api.auth + "/**").permitAll())
                /*Api Authorize*/
                .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, Api.users).permitAll())
                .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.GET, "/").hasAnyAuthority(UserRole.ROLE_ADMIN.toString(), UserRole.ROLE_EMPLOYER.toString(), UserRole.ROLE_JOB_SEEKER.toString()))
                /*End Api Authorize*/
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .authenticationManager(authManager)
                .addFilter(getAuthenticationFilter(authManager))
                .addFilterBefore(new CustomAuthorization(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    protected CustomAuthentication getAuthenticationFilter(AuthenticationManager authManager) {
        final CustomAuthentication filter = new CustomAuthentication(authManager);
        filter.setFilterProcessesUrl("/auth/sign-in");
        return filter;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/js", "/image");
    }
}
