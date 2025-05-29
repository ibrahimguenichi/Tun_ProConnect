package com.ProConnect.users.domain;

import com.ProConnect.entity.AbstractEntity;
import com.ProConnect.users.data.CreateUserRequest;
import com.ProConnect.users.data.UpdateUserPasswordRequest;
import com.ProConnect.users.data.UpdateUserRequest;
import com.ProConnect.util.ApplicationContextProvider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "app_user")
@Getter
@NoArgsConstructor
public class User extends AbstractEntity implements UserDetails {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @Setter
    private Boolean verified = false;
    @Setter
    private String profileImageUrl;
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;
    @Setter
    @OneToOne(mappedBy = "user")
    private VerificationCode verificationCode;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserConnectedAccount> connectedAccounts = new ArrayList<>();

    public User(CreateUserRequest createUserRequest) {
        PasswordEncoder passwordEncoder = ApplicationContextProvider.bean(PasswordEncoder.class);

        this.password = passwordEncoder.encode(createUserRequest.getPassword());
        this.email = createUserRequest.getEmail();
        this.password = passwordEncoder.encode(createUserRequest.getPassword());
        this.firstName = createUserRequest.getFirstName();
        this.lastName = createUserRequest.getLastName();
        this.role = Role.USER;
    }

    public User(OAuth2User oauth2User) {
        User user = new User();
        user.email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        if (name != null) {
            List<String> names = List.of(name.split(" "));

            if (names.size() > 1) {
                user.firstName = names.get(0);
                user.lastName = names.get(1);
            } else {
                user.firstName = name;
            }

            user.verified = true;
            user.role = Role.USER;
        }
    }

    public void addConnectedAccount(UserConnectedAccount userConnectedAccount) {
        connectedAccounts.add(userConnectedAccount);
    }

    public void update(UpdateUserRequest request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
    }

    public void updatePassword(UpdateUserPasswordRequest request) {
        PasswordEncoder passwordEncoder = ApplicationContextProvider.bean(PasswordEncoder.class);
        this.password = request.getNewPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // We need to change this if we want to not allow the user to login before verifying their email to
    // return verified;
    @Override
    public boolean isEnabled() {
        return true;
    }
}
