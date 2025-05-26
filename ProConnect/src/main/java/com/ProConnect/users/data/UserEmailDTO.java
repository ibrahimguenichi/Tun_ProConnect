package com.ProConnect.users.data;

import com.ProConnect.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class UserEmailDTO implements Serializable {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String verificationCode;
    private boolean emailSent;

    public UserEmailDTO(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        //this.verificationCode = user.getVerificationCode().getVerificationCode();
        this.emailSent = user.getVerificationCode().isEmailSent();
    }
}
