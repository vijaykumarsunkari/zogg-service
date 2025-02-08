package com.zogg.zoggservice.dtos;

import java.util.List;
import lombok.Data;

@Data
public class NotificationRequest {
    private List<Integer> userIds;
    private String title;
    private String message;

    // Optional additional fields you might want
    //    private String imageUrl;
    //    private Map<String, String> data;
}
