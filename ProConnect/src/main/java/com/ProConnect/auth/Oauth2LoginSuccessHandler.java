package com.ProConnect.auth;

import com.ProConnect.config.ApplicationProperties;
import com.ProConnect.users.domain.User;
import com.ProConnect.users.domain.UserConnectedAccount;
import com.ProConnect.users.repository.IUserConnectedAccountRepository;
import com.ProConnect.users.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final IUserConnectedAccountRepository userConnectedAccountRepository;
    private final ApplicationProperties applicationProperties;
    private final IUserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        String provider = authenticationToken.getAuthorizedClientRegistrationId();
        String providerId = authentication.getName();
        String email = authenticationToken.getPrincipal().getAttribute("email");

        //First check if we have the user based on a connected account (provider / providerId)
        Optional<UserConnectedAccount> connectedAccount = userConnectedAccountRepository.findByProviderAndProviderId(provider, providerId);
        if (connectedAccount.isPresent()) {
            authenticateUser(connectedAccount.get().getUser(), response);

            return;
        }

        // At this point we don't have a connected  account, so we either find a user by amil and add the connected account
        // or we create a new user
        User existingUser = userRepository.findByEmail(email).orElse(null);
        if (existingUser != null) {
            UserConnectedAccount newConnectedAccount = new UserConnectedAccount(provider, providerId, existingUser);
            existingUser.addConnectedAccount(newConnectedAccount);
            existingUser = userRepository.save(existingUser);
            userConnectedAccountRepository.save(newConnectedAccount);

            authenticateUser(existingUser, response);
        } else {
            User newUser = createUserFromOauth2User(authenticationToken);

            authenticateUser(newUser, response);
        }
    }

    private void authenticateUser(User user, HttpServletResponse response) throws IOException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        response.sendRedirect(applicationProperties.getLoginSuccessUrl());
    }

    private User createUserFromOauth2User(OAuth2AuthenticationToken authenticationToken) {
        User user = new User(authenticationToken.getPrincipal());
        String provider = authenticationToken.getAuthorizedClientRegistrationId();
        String providerId = authenticationToken.getName();
        UserConnectedAccount connectedAccount = new UserConnectedAccount(provider, providerId, user);
        user.addConnectedAccount(connectedAccount);
        userRepository.save(user);
        userConnectedAccountRepository.save(connectedAccount);

        return user;
    }
}
