package com.ProConnect.users.service;

import com.ProConnect.users.data.CreateUserRequest;
import com.ProConnect.users.data.UserResponse;
import com.ProConnect.users.domain.User;

import java.util.List;

public interface IUserService {
    public List<User> retrieveAllUsers();
    public User retrieveUser(Long userId);
    public UserResponse addUser(CreateUserRequest c);
    public void removeUser(Long userId);
    public User modifyUser(User user);
    public boolean isEmailUnique(String email);
}
