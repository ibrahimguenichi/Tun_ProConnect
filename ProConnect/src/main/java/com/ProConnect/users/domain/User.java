package com.ProConnect.users.domain;

import com.ProConnect.entity.AbstractEntity;
import com.ProConnect.users.data.CreateUserRequest;
import com.ProConnect.util.ApplicationContextProvider;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractEntity {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean verified = false;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter
    @OneToOne(mappedBy = "user")
    private VerificationCode verificationCode;

    public User(CreateUserRequest createUserRequest) {
        PasswordEncoder passwordEncoder = ApplicationContextProvider.bean(PasswordEncoder.class);

        this.password = passwordEncoder.encode(createUserRequest.getPassword());
        this.email = createUserRequest.getEmail();
        this.password = passwordEncoder.encode(createUserRequest.getPassword());
        this.firstName = createUserRequest.getFirstName();
        this.lastName = createUserRequest.getLastName();
    }
}
