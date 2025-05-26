package com.ProConnect.users.data;

import com.ProConnect.users.domain.Role;
import com.ProConnect.util.validators.PasswordMatch;
import com.ProConnect.util.validators.Unique;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@PasswordMatch(password = "password", confirmPassword = "confirmPassword")
public class CreateUserRequest {
    @Email
    @Unique(columnName = "email", tableName = "app_user", message = "User with this email already exist")
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Length(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).*$", message = "must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String password;
    private String confirmPassword;
    @NotNull
    private Role role;
}
