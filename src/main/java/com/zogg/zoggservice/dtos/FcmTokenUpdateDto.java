package com.zogg.zoggservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FcmTokenUpdateDto {

    @NotBlank(message = "FCM token cannot be blank")
    private String fcmToken;
}
