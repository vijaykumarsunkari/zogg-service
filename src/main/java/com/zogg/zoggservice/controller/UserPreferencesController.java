package com.zogg.zoggservice.controller;

import com.zogg.zoggservice.dtos.UserPreferencesDTO;
import com.zogg.zoggservice.service.interfaces.UserPreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/preferences")
@RequiredArgsConstructor
public class UserPreferencesController {
    private final UserPreferencesService userPreferencesService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserPreferencesDTO> getUserPreferences(@PathVariable Integer userId) {
        UserPreferencesDTO preferences = userPreferencesService.getUserPreferences(userId);
        return ResponseEntity.ok(preferences);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserPreferencesDTO> updateUserPreferences(
            @PathVariable Integer userId, @RequestBody UserPreferencesDTO preferencesDTO) {
        UserPreferencesDTO updatedPreferences =
                userPreferencesService.updateUserPreferences(userId, preferencesDTO);
        return ResponseEntity.ok(updatedPreferences);
    }
}
