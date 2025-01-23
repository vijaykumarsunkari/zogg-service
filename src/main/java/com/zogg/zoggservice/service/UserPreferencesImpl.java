package com.zogg.zoggservice.service;

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

    @Override
    @Transactional(readOnly = true)
    public UserPreferencesDTO getUserPreferences(Integer userId) { // Changed from Long to Integer
        UserPreferences preferences =
                userPreferencesRepository
                        .findByUserId(userId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "User preferences not found for user: " + userId));
        return convertToDTO(preferences);
    }

    @Override
    @Transactional
    public UserPreferencesDTO updateUserPreferences(
            Integer userId, UserPreferencesDTO dto) { // Changed from Long to Integer
        UserPreferences preferences =
                userPreferencesRepository.findByUserId(userId).orElse(new UserPreferences());
        preferences.setUserId(userId); // Ensure userId is correctly set
        updatePreferencesFromDTO(preferences, dto);
        preferences = userPreferencesRepository.save(preferences);
        return convertToDTO(preferences);
    }

    private UserPreferencesDTO convertToDTO(UserPreferences preferences) {
        UserPreferencesDTO dto = new UserPreferencesDTO();
        dto.setUserId(preferences.getUserId());
        dto.setPreferredCategories(preferences.getPreferredCategories());
        dto.setPreferredBrands(preferences.getPreferredBrands());
        dto.setNotificationEnabled(preferences.getNotificationEnabled());
        dto.setEmailNotifications(preferences.getEmailNotifications());
        dto.setSmsNotifications(preferences.getSmsNotifications());
        dto.setMaxDiscountPreference(preferences.getMaxDiscountPreference());
        dto.setLocationBasedOffers(preferences.getLocationBasedOffers());
        dto.setPreferredDistance(preferences.getPreferredDistance());
        dto.setLanguagePreference(preferences.getLanguagePreference());
        dto.setCurrencyPreference(preferences.getCurrencyPreference());
        return dto;
    }

    private void updatePreferencesFromDTO(UserPreferences preferences, UserPreferencesDTO dto) {
        preferences.setPreferredCategories(dto.getPreferredCategories());
        preferences.setPreferredBrands(dto.getPreferredBrands());
        preferences.setNotificationEnabled(dto.getNotificationEnabled());
        preferences.setEmailNotifications(dto.getEmailNotifications());
        preferences.setSmsNotifications(dto.getSmsNotifications());
        preferences.setMaxDiscountPreference(dto.getMaxDiscountPreference());
        preferences.setLocationBasedOffers(dto.getLocationBasedOffers());
        preferences.setPreferredDistance(dto.getPreferredDistance());
        preferences.setLanguagePreference(dto.getLanguagePreference());
        preferences.setCurrencyPreference(dto.getCurrencyPreference());
    }
}
