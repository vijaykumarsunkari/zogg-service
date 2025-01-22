package com.zogg.zoggservice.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.entity.ZoggCoins;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

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

  private String password;

  @NotBlank(message = "First name is required")
  @JsonProperty("first_name")
  private String firstName;

  @NotBlank(message = "last name is required")
  @JsonProperty("last_name")
  private String lastName;

  @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
  @JsonProperty("phone_number")
  private String phoneNumber;

  @OneToOne(mappedBy = "userId", fetch = FetchType.EAGER)
  @SQLRestriction("deleted = false")
  private ZoggCoins coins;

}
