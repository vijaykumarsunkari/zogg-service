package com.zogg.zoggservice.controller;

import com.google.firebase.messaging.BatchResponse;
import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.NotificationRequest;
import com.zogg.zoggservice.service.interfaces.FirebaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final FirebaseService firebaseService;

    @PostMapping("/send")
    public ApiResponse<BatchResponse> sendNotification(@RequestBody NotificationRequest request) {
        return new ApiResponse<>(
                firebaseService.sendNotificationToUsers(
                        request.getUserIds(), request.getTitle(), request.getMessage()));
    }
}
