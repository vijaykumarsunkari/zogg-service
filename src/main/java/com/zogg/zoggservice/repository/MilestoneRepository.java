package com.zogg.zoggservice.repository;

import com.zogg.zoggservice.entity.Milestone;
import com.zogg.zoggservice.enums.MilestoneType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    List<Milestone> findByMilestoneType(MilestoneType type);

    List<Milestone> findAllByActiveTrue();
}
