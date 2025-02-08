package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.FcmTokenUpdateDto;
import com.zogg.zoggservice.dtos.UpdateUserRequest;
import com.zogg.zoggservice.dtos.UserDto;
import java.util.List;

public interface UserService {
    UserDto updateUser(Integer userId, UpdateUserRequest request);

    UserDto fetchUser(Integer userId);

    List<UserDto> fetchAllUsers();

    FcmTokenUpdateDto updateFcmToken(Integer userId, String fcmToken);
}
