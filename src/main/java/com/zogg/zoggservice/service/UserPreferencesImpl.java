package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.UserPreferencesMapper;
import com.zogg.zoggservice.dtos.UserPreferencesDTO;
import com.zogg.zoggservice.entity.UserPreferences;
import com.zogg.zoggservice.exception.ResourceNotFoundException;
import com.zogg.zoggservice.repository.UserPreferencesRepository;
import com.zogg.zoggservice.service.interfaces.UserPreferencesService;
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

    //    @Override
    //    @Transactional
    //    public UserPreferencesDTO updateUserPreferences(Integer userId, UserPreferencesDTO dto) {
    //        UserPreferences preferences = userPreferencesRepository.findByUserId(userId)
    //                .orElse(new UserPreferences());
    //        preferences.setUserId(userId);
    //        userPreferencesMapper.updateFromDTO(dto, preferences);
    //        preferences = userPreferencesRepository.save(preferences);
    //        return userPreferencesMapper.toDTO(preferences);
    //    }

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

        if (dto.getPreferredCategories() != null) {
            preferences.setPreferredCategories(dto.getPreferredCategories());
        }
        if (dto.getPreferredBrands() != null) {
            preferences.setPreferredBrands(dto.getPreferredBrands());
        }
        if (dto.getNotificationEnabled() != null) {
            preferences.setNotificationEnabled(dto.getNotificationEnabled());
        }
        if (dto.getEmailNotifications() != null) {
            preferences.setEmailNotifications(dto.getEmailNotifications());
        }
        if (dto.getSmsNotifications() != null) {
            preferences.setSmsNotifications(dto.getSmsNotifications());
        }
        if (dto.getMaxDiscountPreference() != null) {
            preferences.setMaxDiscountPreference(dto.getMaxDiscountPreference());
        }
        if (dto.getLocationBasedOffers() != null) {
            preferences.setLocationBasedOffers(dto.getLocationBasedOffers());
        }
        if (dto.getPreferredDistance() != null) {
            preferences.setPreferredDistance(dto.getPreferredDistance());
        }
        if (dto.getLanguagePreference() != null) {
            preferences.setLanguagePreference(dto.getLanguagePreference());
        }
        if (dto.getCurrencyPreference() != null) {
            preferences.setCurrencyPreference(dto.getCurrencyPreference());
        }

        preferences = userPreferencesRepository.save(preferences);
        return userPreferencesMapper.toDTO(preferences);
    }
}
