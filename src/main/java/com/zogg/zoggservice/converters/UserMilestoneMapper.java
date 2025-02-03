package com.zogg.zoggservice.converters;

import com.zogg.zoggservice.dtos.MilestoneDto;
import com.zogg.zoggservice.dtos.UserMilestoneResponseDto;
import com.zogg.zoggservice.entity.Milestone;
import com.zogg.zoggservice.entity.UserMilestoneProgress;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMilestoneMapper {

    UserMilestoneMapper INSTANCE = Mappers.getMapper(UserMilestoneMapper.class);

    @Mappings({
        @Mapping(target = "name", source = "milestone.name"),
        @Mapping(target = "description", source = "milestone.description"),
        @Mapping(target = "targetValue", source = "milestone.targetValue"),
        @Mapping(target = "rewardCoins", source = "milestone.rewardCoins"),
        @Mapping(target = "milestoneType", source = "milestone.milestoneType"),
        @Mapping(target = "timeFrame", source = "milestone.timeFrame"),
        @Mapping(target = "startTime", source = "milestone.startTime"),
        @Mapping(target = "endTime", source = "milestone.endTime"),
        @Mapping(target = "progress", source = "userMilestoneProgress.progress"),
        @Mapping(target = "completed", source = "userMilestoneProgress.completed"),
        @Mapping(target = "redeemed", source = "userMilestoneProgress.redeemed")
    })
    UserMilestoneResponseDto toResponse(
            UserMilestoneProgress userMilestoneProgress, Milestone milestone);

    MilestoneDto toMilestoneDto(Milestone milestone);

    List<MilestoneDto> toMilestoneDtos(List<Milestone> milestones);
}
