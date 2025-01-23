package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.AuthRequest;
import com.zogg.zoggservice.dtos.TokenResponse;
import com.zogg.zoggservice.dtos.UserDto;
import com.zogg.zoggservice.dtos.VerifyOtpRequest;
import com.zogg.zoggservice.entity.User;
import jakarta.validation.Valid;

public interface AuthService {

    User register(UserDto userDto);

    TokenResponse login(AuthRequest request);

    String sendOtp(String phoneNumber);

    TokenResponse verifyOtp(@Valid VerifyOtpRequest request);
}
