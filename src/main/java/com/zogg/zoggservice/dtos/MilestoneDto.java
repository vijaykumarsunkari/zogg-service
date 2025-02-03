package com.zogg.zoggservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zogg.zoggservice.enums.MilestoneType;
import com.zogg.zoggservice.enums.TimeFrame;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MilestoneDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("target_value")
    private Integer targetValue;

    @JsonProperty("milestone_type")
    private MilestoneType milestoneType;

    @JsonProperty("time_frame")
    private TimeFrame timeFrame;

    @JsonProperty("reward_coins")
    private Integer rewardCoins;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @JsonProperty("active")
    private boolean active;
}
