package com.ProConnect.auth;

import com.ProConnect.users.domain.User;
import com.ProConnect.util.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.Optional;

@Slf4j
public class SecurityUtil {
    private static final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    /**
     * Get the authenticated user from the SecurityContextHolder
     * @throws com.ProConnect.util.exception.ApiException if the user is not found in the SecurityContextHolder
     */
    public static User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        } else {
            log.error("User requested but not found in SecurityContext");
            throw ApiException.builder().statusCode(401).message("Authentication required").build();
        }
    }

    public static Optional<User> getOptionalAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return Optional.of((User) principal);
        } else {
            return Optional.empty();
        }
    }
}
