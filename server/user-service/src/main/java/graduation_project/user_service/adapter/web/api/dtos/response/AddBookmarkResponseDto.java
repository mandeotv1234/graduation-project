package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.AddBookmarkResponse;

public record AddBookmarkResponseDto(Long id, Long userId, Long postId) {

    public static AddBookmarkResponseDto from(AddBookmarkResponse response) {
        return new AddBookmarkResponseDto(response.id(), response.userId(), response.postId());
    }
}
