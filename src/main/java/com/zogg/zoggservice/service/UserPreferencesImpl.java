package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.UserPreferencesMapper;
import com.zogg.zoggservice.dtos.UserPreferencesDTO;
import com.zogg.zoggservice.entity.UserPreferences;
import com.zogg.zoggservice.exception.ResourceNotFoundException;
import com.zogg.zoggservice.repository.UserPreferencesRepository;
import com.zogg.zoggservice.service.interfaces.UserPreferencesService;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPreferencesImpl implements UserPreferencesService {
    private final UserPreferencesRepository userPreferencesRepository;
    private final UserPreferencesMapper userPreferencesMapper;

    @Override
    @Transactional(readOnly = true)
    public UserPreferencesDTO getUserPreferences(Integer userId) {
        UserPreferences preferences =
                userPreferencesRepository
                        .findByUserId(userId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "User preferences not found for user: " + userId));
        return userPreferencesMapper.toDTO(preferences);
    }

    @Override
    @Transactional
    public UserPreferencesDTO updateUserPreferences(Integer userId, UserPreferencesDTO dto) {
        UserPreferences preferences =
                userPreferencesRepository
                        .findByUserId(userId)
                        .orElseGet(
                                () -> {
                                    UserPreferences newPreferences = new UserPreferences();
                                    newPreferences.setUserId(userId);
                                    return newPreferences;
                                });

        updateIfNotNull(dto.getPreferredCategories(), preferences::setPreferredCategories);
        updateIfNotNull(dto.getPreferredBrands(), preferences::setPreferredBrands);
        updateIfNotNull(dto.getNotificationEnabled(), preferences::setNotificationEnabled);
        updateIfNotNull(dto.getEmailNotifications(), preferences::setEmailNotifications);
        updateIfNotNull(dto.getSmsNotifications(), preferences::setSmsNotifications);
        updateIfNotNull(dto.getMaxDiscountPreference(), preferences::setMaxDiscountPreference);
        updateIfNotNull(dto.getLocationBasedOffers(), preferences::setLocationBasedOffers);
        updateIfNotNull(dto.getPreferredDistance(), preferences::setPreferredDistance);
        updateIfNotNull(dto.getLanguagePreference(), preferences::setLanguagePreference);
        updateIfNotNull(dto.getCurrencyPreference(), preferences::setCurrencyPreference);

        preferences = userPreferencesRepository.save(preferences);
        return userPreferencesMapper.toDTO(preferences);
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
