package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.FollowResponse;

public record FollowResponseDto(Long id, Long contributorId, Long seekerId)  {
    public static FollowResponseDto from(FollowResponse response) {
        return new FollowResponseDto(response.id(), response.contributorId(), response.seekerId());
    }
}