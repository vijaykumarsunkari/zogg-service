package com.zogg.zoggservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_preferences")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "preferred_categories")
    private String preferredCategories;

    @Column(name = "preferred_brands")
    private String preferredBrands;

    @Column(name = "notification_enabled", nullable = false)
    private Boolean notificationEnabled;

    @Column(name = "email_notifications", nullable = false)
    private Boolean emailNotifications;

    @Column(name = "sms_notifications", nullable = false)
    private Boolean smsNotifications;

    @Column(name = "max_discount_preference")
    private Integer maxDiscountPreference;

    @Column(name = "location_based_offers", nullable = false)
    private Boolean locationBasedOffers;

    @Column(name = "preferred_distance", nullable = false)
    private Double preferredDistance;

    @Column(name = "language_preference", nullable = false)
    private String languagePreference;

    @Column(name = "currency_preference", nullable = false)
    private String currencyPreference;

    @Column(name = "created_at", updatable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
}
