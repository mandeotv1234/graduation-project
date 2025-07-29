package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetUserPostsResponse;

import java.time.LocalDateTime;
import java.util.List;

public record GetUserPostsResponseDto(
    Long id,
    String title,
    String content,
    LocalDateTime postedAt,
    boolean isPublished,
    List<String> tags,
    boolean isBookmarked,
    Long views,
    long likes,
    boolean isLiked,
    int comments
) {
    public static GetUserPostsResponseDto fromResponse(GetUserPostsResponse model) {
        return new GetUserPostsResponseDto(
            model.id(),
            model.title(),
            model.content(),
            model.time(),
            model.isPublished(),
            model.tags(),
            model.isBookmarked(),
            model.views(),
            model.likes(),
            model.isLiked(),
            model.comments()
        );
    }

    public static List<GetUserPostsResponseDto> fromResponses(List<GetUserPostsResponse> responses) {
        return responses.stream()
            .map(GetUserPostsResponseDto::fromResponse)
            .toList();
    }
}
