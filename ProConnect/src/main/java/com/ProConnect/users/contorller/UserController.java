package com.ProConnect.users.contorller;

import com.ProConnect.users.data.CreateUserRequest;
import com.ProConnect.users.data.UserResponse;
import com.ProConnect.users.domain.User;
import com.ProConnect.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("add")
    public UserResponse createUser(@Valid @RequestBody CreateUserRequest request) {

        return userService.addUser(request);
    }
}
