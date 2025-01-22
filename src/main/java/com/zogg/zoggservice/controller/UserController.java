package com.zogg.zoggservice.controller;

import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.UpdateUserRequest;
import com.zogg.zoggservice.dtos.UserDto;
import com.zogg.zoggservice.entity.User;
import com.zogg.zoggservice.service.interfaces.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/{userId}")
    public ApiResponse<User> updateUser(@PathVariable Integer userId,
                                        @RequestBody UpdateUserRequest request) {
        return new ApiResponse<>(userService.updateUser(userId, request));
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserDto> getUser(@PathVariable Integer userId) {

        return new ApiResponse<>(userService.fetchUser(userId));
    }
}
