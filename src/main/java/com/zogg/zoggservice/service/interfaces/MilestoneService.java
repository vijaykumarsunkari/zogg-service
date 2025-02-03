package com.zogg.zoggservice.service.interfaces;

import com.zogg.zoggservice.dtos.MilestoneDto;
import com.zogg.zoggservice.dtos.UserMilestoneResponseDto;
import java.util.List;

public interface MilestoneService {

    void updateProgress(Integer userId, String type, int value);

    List<UserMilestoneResponseDto> getUserProgress(Integer userId);

    MilestoneDto createMilestone(MilestoneDto dto);

    List<MilestoneDto> fetchMileStones();

    void redeemUserMilestone(Integer userId, Long milestoneId);
}
