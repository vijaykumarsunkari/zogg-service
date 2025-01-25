package com.zogg.zoggservice.dtos;

import lombok.Data;

@Data
public class UserPreferencesDTO {

    private Integer userId;
    private String preferredCategories;
    private String preferredBrands;
    private Boolean notificationEnabled;
    private Boolean emailNotifications;
    private Boolean smsNotifications;
    private Integer maxDiscountPreference;
    private Boolean locationBasedOffers;
    private Double preferredDistance;
    private String languagePreference;
    private String currencyPreference;
}
