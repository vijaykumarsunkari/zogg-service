package com.zogg.zoggservice.controller;

import com.zogg.zoggservice.dtos.ApiResponse;
import com.zogg.zoggservice.dtos.MilestoneDto;
import com.zogg.zoggservice.dtos.UserMilestoneResponseDto;
import com.zogg.zoggservice.service.interfaces.MilestoneService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/milestone")
public class MilestoneController {

    @Autowired private MilestoneService milestoneService;

    @PostMapping
    public ApiResponse<MilestoneDto> createMilestone(@RequestBody MilestoneDto milestoneDto) {
        return new ApiResponse<>(milestoneService.createMilestone(milestoneDto));
    }

    @GetMapping
    public ApiResponse<List<MilestoneDto>> getMilestones() {
        return new ApiResponse<>(milestoneService.fetchMileStones());
    }

    @GetMapping("/{userId}/progress")
    public List<UserMilestoneResponseDto> getUserMilestoneProgress(@PathVariable Integer userId) {
        return milestoneService.getUserProgress(userId);
    }

    @PostMapping("/{userId}/update")
    public ApiResponse<?> updateMilestoneProgress(
            @PathVariable Integer userId, @RequestParam String type, @RequestParam int value) {
        milestoneService.updateProgress(userId, type, value);
        return new ApiResponse<>("Progress Updated");
    }

    @PostMapping("/{user_id}/redeem")
    public ApiResponse<?> redeemMilestone(
            @PathVariable Integer userId, @RequestParam Long milestoneId) {
        milestoneService.redeemUserMilestone(userId, milestoneId);
        return new ApiResponse<>("Milestone Redeemed");
    }
}
