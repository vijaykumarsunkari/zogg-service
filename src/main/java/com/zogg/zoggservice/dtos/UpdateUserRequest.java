package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.enums.Gender;
import java.time.LocalDate;
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
public class UpdateUserRequest {
    private Integer id;
    private String username;
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

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

    @JsonProperty("updated_by")
    private Integer updatedBy;
}
