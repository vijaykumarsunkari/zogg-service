package com.zogg.zoggservice.service;

import com.zogg.zoggservice.converters.UserMilestoneMapper;
import com.zogg.zoggservice.dtos.MilestoneDto;
import com.zogg.zoggservice.dtos.UserMilestoneResponseDto;
import com.zogg.zoggservice.entity.Milestone;
import com.zogg.zoggservice.entity.UserMilestoneProgress;
import com.zogg.zoggservice.enums.CoinTypeEnum;
import com.zogg.zoggservice.enums.MilestoneType;
import com.zogg.zoggservice.enums.TransactionType;
import com.zogg.zoggservice.repository.MilestoneRepository;
import com.zogg.zoggservice.repository.UserMilestoneProgressRepository;
import com.zogg.zoggservice.service.interfaces.CoinService;
import com.zogg.zoggservice.service.interfaces.MilestoneService;
import com.zogg.zoggservice.utils.CommonUtils;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

    private final UserMilestoneProgressRepository progressRepository;

    private final MilestoneRepository milestoneRepository;

    private final CoinService coinService;

    @Transactional
    public void updateProgress(Integer userId, String type, int value) {
        List<Milestone> milestones =
                milestoneRepository.findByMilestoneType(MilestoneType.valueOf(type));

        List<UserMilestoneProgress> milestoneProgressList = new ArrayList<>();

        for (Milestone milestone : milestones) {
            UserMilestoneProgress progress =
                    progressRepository
                            .findByUserIdAndMilestoneIdAndCompleted(
                                    userId, milestone.getId(), false)
                            .orElse(
                                    UserMilestoneProgress.builder()
                                            .milestoneId(milestone.getId())
                                            .progress(0)
                                            .userId(userId)
                                            .build());

            progress.setProgress(progress.getProgress() + value);

            if (progress.getProgress() >= milestone.getTargetValue()) {
                progress.setProgress(milestone.getTargetValue());
                progress.setCompleted(true);
            }
            progress.setUpdatedAt(LocalDateTime.now());

            milestoneProgressList.add(progress);
        }

        progressRepository.saveAll(milestoneProgressList);
    }

    @Override
    public List<UserMilestoneResponseDto> getUserProgress(Integer userId) {

        List<UserMilestoneProgress> userMilestoneProgressList =
                progressRepository.findByUserId(userId);

        List<UserMilestoneResponseDto> userMilestoneResponseDtoList = new ArrayList<>();

        for (UserMilestoneProgress userMilestoneProgress : userMilestoneProgressList) {

            Milestone milestone =
                    milestoneRepository
                            .findById(userMilestoneProgress.getMilestoneId())
                            .orElseThrow(
                                    () -> CommonUtils.logAndGetException("Milestone not found"));
            userMilestoneResponseDtoList.add(
                    UserMilestoneMapper.INSTANCE.toResponse(userMilestoneProgress, milestone));
        }
        return userMilestoneResponseDtoList;
    }

    @Override
    public MilestoneDto createMilestone(MilestoneDto dto) {
        Milestone milestone = new Milestone();
        milestone.setName(dto.getName());
        milestone.setMilestoneType(dto.getMilestoneType());
        milestone.setDescription(dto.getDescription());
        milestone.setTargetValue(dto.getTargetValue());
        milestone.setRewardCoins(dto.getRewardCoins());
        milestone.setStartTime(dto.getStartTime());
        milestone.setEndTime(dto.getEndTime());
        milestone.setActive(dto.isActive());

        return UserMilestoneMapper.INSTANCE.toMilestoneDto(milestoneRepository.save(milestone));
    }

    @Override
    public List<MilestoneDto> fetchMileStones() {

        return UserMilestoneMapper.INSTANCE.toMilestoneDtos(
                milestoneRepository.findAllByActiveTrue());
    }

    @Override
    public void redeemUserMilestone(Integer userId, Long milestoneId) {
        UserMilestoneProgress userMilestoneProgress =
                progressRepository
                        .findByUserIdAndMilestoneIdAndCompleted(userId, milestoneId, true)
                        .orElseThrow(
                                () ->
                                        CommonUtils.logAndGetException(
                                                "Milestone not found or not completed"));
        Milestone milestone =
                milestoneRepository
                        .findById(milestoneId)
                        .orElseThrow(
                                () ->
                                        CommonUtils.logAndGetException(
                                                "Milestone not found for the given milestone id"));

        userMilestoneProgress.setRedeemed(true);
        progressRepository.save(userMilestoneProgress);

        coinService.updateCoins(
                userId,
                CoinTypeEnum.ZOGG_COIN,
                TransactionType.CREDIT,
                Long.valueOf(milestone.getRewardCoins()));
    }
}
