package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.FcmTokenMapper;
import com.zogg.zoggservice.converters.UserMapper;
import com.zogg.zoggservice.dtos.FcmTokenUpdateDto;
import com.zogg.zoggservice.dtos.UpdateUserRequest;
import com.zogg.zoggservice.dtos.UserDto;
import com.zogg.zoggservice.entity.User;
import com.zogg.zoggservice.exception.ResourceNotFoundException;
import com.zogg.zoggservice.repository.UserRepository;
import com.zogg.zoggservice.service.interfaces.UserService;
import com.zogg.zoggservice.utils.CommonUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto updateUser(Integer userId, UpdateUserRequest request) {

        User user =
                userRepository
                        .findById(request.getId())
                        .orElseThrow(
                                () ->
                                        CommonUtils.logAndGetException(
                                                "User not found " + request.getId()));

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

        user.setUsername(
                Objects.nonNull(request.getUsername())
                        ? request.getUsername()
                        : user.getUsername());
        user.setEmail(Objects.nonNull(request.getEmail()) ? request.getEmail() : user.getEmail());
        user.setFirstName(
                Objects.nonNull(request.getFirstName())
                        ? request.getFirstName()
                        : user.getFirstName());
        user.setLastName(
                Objects.nonNull(request.getLastName())
                        ? request.getLastName()
                        : user.getLastName());
        user.setProfilePicture(
                Objects.nonNull(request.getProfilePicture())
                        ? request.getProfilePicture()
                        : user.getProfilePicture());
        user.setDateOfBirth(
                Objects.nonNull(request.getDateOfBirth())
                        ? request.getDateOfBirth()
                        : user.getDateOfBirth());
        user.setGender(
                Objects.nonNull(request.getGender()) ? request.getGender() : user.getGender());
        user.setAddress(
                Objects.nonNull(request.getAddress()) ? request.getAddress() : user.getAddress());
        user.setCity(Objects.nonNull(request.getCity()) ? request.getCity() : user.getCity());
        user.setState(Objects.nonNull(request.getState()) ? request.getState() : user.getState());
        user.setCountry(
                Objects.nonNull(request.getCountry()) ? request.getCountry() : user.getCountry());
        user.setZipCode(
                Objects.nonNull(request.getZipCode()) ? request.getZipCode() : user.getZipCode());

        user.setUpdatedBy(user.getUserId());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save((user));
        return UserMapper.INSTANCE.toDto(savedUser);
    }

    @Override
    public UserDto fetchUser(Integer userId) {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> CommonUtils.logAndGetException("User not found"));
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public List<UserDto> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FcmTokenUpdateDto updateFcmToken(Integer userId, String fcmToken) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "User not found with id: " + userId));

        if (fcmToken == null || fcmToken.trim().isEmpty()) {
            throw new IllegalArgumentException("FCM token cannot be empty");
        }

        FcmTokenUpdateDto tokenDto = new FcmTokenUpdateDto(fcmToken);
        FcmTokenMapper.INSTANCE.updateFcmTokenFromDto(tokenDto, user);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return tokenDto;
    }
}
