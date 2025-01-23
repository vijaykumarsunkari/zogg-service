package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.UpdateUserRequest;
import com.zogg.zoggservice.dtos.UserDto;
import com.zogg.zoggservice.entity.User;

public interface UserService {
    User updateUser(Integer userId, UpdateUserRequest request);

    UserDto fetchUser(Integer userId);
}
