package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.UnfollowResponse;

public record UnfollowResponseDto(String message) {
    public static UnfollowResponseDto from(UnfollowResponse response) {
        return new UnfollowResponseDto(response.message());
    }
}