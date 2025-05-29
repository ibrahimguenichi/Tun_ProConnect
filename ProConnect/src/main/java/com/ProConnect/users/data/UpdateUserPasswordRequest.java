package com.ProConnect.users.data;

import com.ProConnect.util.Client;
import com.ProConnect.util.validators.PasswordMatch;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Client
@PasswordMatch(password ="newPassword", confirmPassword = "confirmPassword")
public class UpdateUserPasswordRequest {
    private String oldPassword;
    @NotNull
    @Length(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).*$", message = "must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String newPassword;
    private String confirmPassword;
    private String passwordResetToken;
}
