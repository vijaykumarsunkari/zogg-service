package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtpRequest {

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String username;
    private String otp;
}
