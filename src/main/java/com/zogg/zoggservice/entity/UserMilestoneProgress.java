package com.zogg.zoggservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_milestone_progress")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserMilestoneProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "milestone_id")
    private Long milestoneId;

    private Integer progress;

    private boolean completed;

    private boolean redeemed;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;
}
