package com.zogg.zoggservice.repository;

import com.zogg.zoggservice.entity.UserMilestoneProgress;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMilestoneProgressRepository
        extends JpaRepository<UserMilestoneProgress, Long> {
    Optional<UserMilestoneProgress> findByUserIdAndMilestoneIdAndCompleted(
            Integer userId, Long milestoneId, boolean completed);

    List<UserMilestoneProgress> findByUserId(Integer userId);
}
