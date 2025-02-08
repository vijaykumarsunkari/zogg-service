package com.zogg.zoggservice.service.interfaces;

import com.google.firebase.messaging.BatchResponse;
import java.util.List;

public interface FirebaseService {
    BatchResponse sendNotificationToMultipleTokens(
            List<String> fcmTokens, String title, String body);

    BatchResponse sendNotificationToUsers(List<Integer> userIds, String title, String body);
}
