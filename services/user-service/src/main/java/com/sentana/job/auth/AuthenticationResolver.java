package com.sentana.job.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationResolver {
    static Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    public static String getAuthentication(){
        return authentication.getName();
    }

    public static List<String> getRoles(){
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
