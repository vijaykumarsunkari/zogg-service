package com.zogg.zoggservice.controller;

import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.FcmTokenUpdateDto;
import com.zogg.zoggservice.dtos.UpdateUserRequest;
import com.zogg.zoggservice.dtos.UserDto;
import com.zogg.zoggservice.service.interfaces.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/{userId}")
    public ApiResponse<UserDto> updateUser(
            @PathVariable Integer userId, @RequestBody UpdateUserRequest request) {
        return new ApiResponse<>(userService.updateUser(userId, request));
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserDto> getUser(@PathVariable Integer userId) {

        return new ApiResponse<>(userService.fetchUser(userId));
    }

    @PutMapping("/{userId}/fcm-token")
    public ApiResponse<FcmTokenUpdateDto> updateFcmToken(
            @PathVariable Integer userId, @Valid @RequestBody FcmTokenUpdateDto tokenDto) {
        return new ApiResponse<>(userService.updateFcmToken(userId, tokenDto.getFcmToken()));
    }

    @GetMapping("")
    public ApiResponse<List<UserDto>> getAllUsers() {
        return new ApiResponse<>(userService.fetchAllUsers());
    }
}
