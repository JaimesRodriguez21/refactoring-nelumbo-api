package com.nelumbo.api.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtils {

    public static String obtenerUsernameUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && !authentication.getAuthorities().isEmpty()) {
            return authentication.getName();
        }
        return null;
    }

}

