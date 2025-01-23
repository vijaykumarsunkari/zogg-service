package com.zogg.zoggservice.controller;

import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.AuthRequest;
import com.zogg.zoggservice.dtos.TokenResponse;
import com.zogg.zoggservice.dtos.UserDto;
import com.zogg.zoggservice.dtos.VerifyOtpRequest;
import com.zogg.zoggservice.entity.User;
import com.zogg.zoggservice.service.interfaces.AuthService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Transactional
    public ApiResponse<User> register(@RequestBody UserDto userDto) {
        return new ApiResponse<>(authService.register(userDto));
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody AuthRequest request) {
        return new ApiResponse<>(authService.login(request));
    }

    @PostMapping("/send-otp")
    public ApiResponse<String> sendLoginOtp(@RequestParam("phone_number") String phoneNumber) {
        return new ApiResponse<>(authService.sendOtp(phoneNumber));
    }

    @PostMapping("/verify-otp")
    public ApiResponse<TokenResponse> verifyOtp(@RequestBody @Valid VerifyOtpRequest request) {
        return new ApiResponse<>(authService.verifyOtp(request));
    }
}
