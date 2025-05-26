package com.ProConnect.users.data;

import com.ProConnect.users.domain.User;
import com.ProConnect.util.Client;
import jakarta.annotation.Nullable;
import lombok.Data;
import com.ProConnect.users.domain.Role;

@Data
@Client
public class UserResponse {
    private Long id;
    private Role role;
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
    private String email;
    //@Nullable
    //private String profileImageUrl;

    public UserResponse(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        //this.profileImageUrl = user.getProfileImageUrl();
        //user.getConnectedAccounts().forEach((provider) -> {
        //this.connectedAccounts.add(new ConnectedAccountResponse(provider.getProvider(), provider.getConnectedAt()));
        //});
    }
}
