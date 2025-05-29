package com.ProConnect.auth.controller;

import com.ProConnect.auth.data.LoginRequest;
import com.ProConnect.auth.service.AuthService;
import com.ProConnect.util.Client;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@Slf4j
@Client
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody LoginRequest body
    ) {
        authService.login(request, response, body);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getSession(HttpServletRequest request) {
        return ResponseEntity.ok(authService.getSession(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);

        return ResponseEntity.ok().build();
    }

    /**
     * We don't have to do anything in this endpoint, the CsrfFilter will handle it.
     * This endpoint should be invoked by the frontend to get the CSRF token.
     */
    @GetMapping("/csrf")
    public ResponseEntity<?> csrf() {return ResponseEntity.ok().build();}
}
