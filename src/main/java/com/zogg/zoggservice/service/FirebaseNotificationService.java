package com.zogg.zoggservice.service;

import com.google.firebase.messaging.*;
import com.zogg.zoggservice.exception.ZoggServiceException;
import com.zogg.zoggservice.repository.UserRepository;
import com.zogg.zoggservice.service.interfaces.FirebaseService;
import com.zogg.zoggservice.utils.CommonUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseNotificationService implements FirebaseService {

    private final UserRepository userRepository;

    @Override
    public BatchResponse sendNotificationToMultipleTokens(
            List<String> fcmTokens, String title, String body) {
        try {
            List<String> validTokens = CommonUtils.validateAndFilterTokens(fcmTokens);

            if (validTokens.isEmpty()) {
                throw new ZoggServiceException("No valid FCM tokens found");
            }

            MulticastMessage message = CommonUtils.buildMulticastMessage(validTokens, title, body);
            BatchResponse response =
                    FirebaseMessaging.getInstance().sendMulticast(message); // sendEachForMulticast

            CommonUtils.logNotificationStatus(response, validTokens);

            return response;

        } catch (FirebaseMessagingException e) {
            throw CommonUtils.logAndGetException("Failed to send notifications", e);
        }
    }

    @Override
    public BatchResponse sendNotificationToUsers(List<Integer> userIds, String title, String body) {
        if (userIds == null || userIds.isEmpty()) {
            throw new ZoggServiceException("User IDs cannot be empty");
        }

        List<String> fcmTokens = fetchFcmTokensForUsers(userIds);
        return sendNotificationToMultipleTokens(fcmTokens, title, body);
    }

    private List<String> fetchFcmTokensForUsers(List<Integer> userIds) {
        return userRepository.findFcmTokensByUserIds(userIds);
    }
}
