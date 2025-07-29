package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.AddBookmarkResponse;
import com.mgmtp.ihcmc.hive.server.application.usecases.response.AddLikeResponse;

public record AddLikeResponseDto(Long userId, Long postId) {

    public static AddLikeResponseDto from(AddLikeResponse response) {
        return new AddLikeResponseDto(response.userId(), response.postId());
    }
}
