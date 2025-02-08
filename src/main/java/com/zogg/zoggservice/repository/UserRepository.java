package com.zogg.zoggservice.repository;

import com.zogg.zoggservice.entity.User;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(
            @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
                    String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT u.fcmToken FROM User u WHERE u.userId IN :userIds AND u.fcmToken IS NOT NULL")
    List<String> findFcmTokensByUserIds(@Param("userIds") List<Integer> userIds);
}
