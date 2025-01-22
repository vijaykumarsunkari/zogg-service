package com.zogg.zoggservice.repository;

import com.zogg.zoggservice.entity.User;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByPhoneNumber(String phoneNumber);

  boolean existsByPhoneNumber(
      @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits") String phoneNumber);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);
}
