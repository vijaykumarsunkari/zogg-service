package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.UserMapper;
import com.zogg.zoggservice.dtos.UpdateUserRequest;
import com.zogg.zoggservice.dtos.UserDto;
import com.zogg.zoggservice.entity.User;
import com.zogg.zoggservice.repository.UserRepository;
import com.zogg.zoggservice.service.interfaces.UserService;
import com.zogg.zoggservice.utils.CommonUtils;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User updateUser(Integer userId, UpdateUserRequest request) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> CommonUtils.logAndGetException("User not found"));

        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw CommonUtils.logAndGetException("Username already taken");
            }
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw CommonUtils.logAndGetException("Email already registered");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getProfilePicture() != null) {
            user.setProfilePicture(request.getProfilePicture());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }

        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public UserDto fetchUser(Integer userId) {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> CommonUtils.logAndGetException("User not found"));
        return UserMapper.INSTANCE.toDto(user);
    }
}
