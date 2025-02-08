package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserDto {

    private String username;

    @JsonProperty("user_id")
    private Integer userId;

    private String email;

    @NotBlank(message = "First name is required")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "last name is required")
    @JsonProperty("last_name")
    private String lastName;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("profile_picture")
    private String profilePicture;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    private Gender gender;
    private String address;
    private String city;
    private String state;
    private String country;

    @JsonProperty("zip_code")
    private String zipCode;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("updated_by")
    private Integer updatedBy;
}
