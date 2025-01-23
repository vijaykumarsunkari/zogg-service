package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.UserMapper;
import com.zogg.zoggservice.dtos.AuthRequest;
import com.zogg.zoggservice.dtos.TokenResponse;
import com.zogg.zoggservice.dtos.UserDto;
import com.zogg.zoggservice.dtos.VerifyOtpRequest;
import com.zogg.zoggservice.entity.User;
import com.zogg.zoggservice.repository.UserRepository;
import com.zogg.zoggservice.service.interfaces.AuthService;
import com.zogg.zoggservice.service.interfaces.RedisService;
import com.zogg.zoggservice.utils.CommonUtils;
import com.zogg.zoggservice.utils.JwtUtil;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final JwtUtil jwtUtil;

    private static final long OTP_EXPIRY_SECONDS = 300L; // 5 minutes

    @Override
    public User register(UserDto userDto) {
        validateUserInput(userDto);

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = UserMapper.INSTANCE.toEntity(userDto);

        user.setCreatedAt(LocalDateTime.now());
        user.setVerified(false);

        sendOtpToUser(user.getPhoneNumber());

        return userRepository.save(user);
    }

    @Override
    public TokenResponse login(AuthRequest request) {
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw CommonUtils.logAndGetException("Invalid phone number or password.");
        }

        return generateTokenResponse(user);
    }

    @Override
    public String sendOtp(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw CommonUtils.logAndGetException("No user found with phone number: " + phoneNumber);
        }

        Long otp = sendOtpToUser(phoneNumber);
        return "OTP sent successfully. OTP: " + otp;
    }

    @Override
    public TokenResponse verifyOtp(@Valid VerifyOtpRequest request) {
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber());

        if (user == null) {
            throw CommonUtils.logAndGetException("User not found for this phone number.");
        }

        String storedOtp =
                redisService
                        .get(CommonUtils.getRedisKeyForOtp(request.getPhoneNumber()))
                        .orElseThrow(
                                () -> CommonUtils.logAndGetException("OTP expired or not found."));

        if (!storedOtp.equals(request.getOtp())) {
            throw CommonUtils.logAndGetException("Invalid OTP provided.");
        }

        if (Boolean.FALSE.equals(user.getVerified())) {
            user.setVerified(true);
            userRepository.save(user);
        }

        return generateTokenResponse(user);
    }

    private void validateUserInput(UserDto userDto) {
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw CommonUtils.logAndGetException("Phone number already registered.");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw CommonUtils.logAndGetException("Email already registered.");
        }
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw CommonUtils.logAndGetException("Username already taken.");
        }
    }

    private TokenResponse generateTokenResponse(User user) {
        String token = jwtUtil.generateToken(user.getUsername(), user.getPhoneNumber());

        return TokenResponse.builder()
                .user(UserMapper.INSTANCE.toDto(user))
                .token(token)
                .tokenType("Bearer")
                .expiresIn(999998)
                .build();
    }

    private Long sendOtpToUser(String phoneNumber) {

        Optional<String> storedOtp = redisService.get(CommonUtils.getRedisKeyForOtp(phoneNumber));

        if (storedOtp.isPresent()) {
            return Long.parseLong(storedOtp.get());
        }

        Long otp = CommonUtils.generateOtp(phoneNumber);

        redisService.setWithExpiry(
                CommonUtils.getRedisKeyForOtp(phoneNumber),
                String.valueOf(otp),
                OTP_EXPIRY_SECONDS);

        // TODO : Send SMS to phone number via SMS service
        System.out.println("Generated OTP :" + otp);
        return otp;
    }
}
