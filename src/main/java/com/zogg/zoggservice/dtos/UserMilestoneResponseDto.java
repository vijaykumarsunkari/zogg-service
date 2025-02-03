package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.enums.MilestoneType;
import com.zogg.zoggservice.enums.TimeFrame;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserMilestoneResponseDto {

    @JsonProperty("user_id")
    private Long userId;

    private Integer progress;

    private boolean completed;

    @JsonProperty("target_value")
    private Integer targetValue;

    private String name;

    private String description;

    @JsonProperty("reward_coins")
    private Integer rewardCoins;

    @JsonProperty("milestone_type")
    private MilestoneType milestoneType;

    @JsonProperty("time_frame")
    private TimeFrame timeFrame;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    private boolean redeemed;
}
