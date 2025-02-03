package com.zogg.zoggservice.entity;

import com.zogg.zoggservice.enums.MilestoneType;
import com.zogg.zoggservice.enums.TimeFrame;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "milestones")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name = "target_value")
    private Integer targetValue;

    @Column(name = "reward_coins")
    private Integer rewardCoins;

    @Column(name = "milestone_type")
    @Enumerated(EnumType.STRING)
    private MilestoneType milestoneType;

    @Column(name = "time_frame")
    @Enumerated(EnumType.STRING)
    private TimeFrame timeFrame;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private boolean active;
}
