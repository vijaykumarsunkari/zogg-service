package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.UserPreferencesDTO;

public interface UserPreferencesService {
    UserPreferencesDTO getUserPreferences(Integer userId);

    UserPreferencesDTO updateUserPreferences(Integer userId, UserPreferencesDTO preferencesDTO);
}
