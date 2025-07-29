package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetBookmarkPostResponse;

import java.time.LocalDateTime;
import java.util.List;

public record GetBookmarkedPostResponseDto(
        Long id,
        String title,
        String content,
        String postedAt,
        boolean isBookmarked,
        LocalDateTime bookmarkedAt,
        List<String> tags,
        AuthorDto author,
        long likes,
        boolean isLiked,
        int comments
) {

    public static List<GetBookmarkedPostResponseDto> fromResponses(List<GetBookmarkPostResponse> responses) {
        return responses.stream()
                .map(GetBookmarkedPostResponseDto::fromResponse)
                .toList();
    }

    public static GetBookmarkedPostResponseDto fromResponse(GetBookmarkPostResponse response) {
        return new GetBookmarkedPostResponseDto(
                response.id(),
                response.title(),
                response.content(),
                response.postedAt().toString(),
                response.isBookmarked(),
                response.createdAt(),
                response.tags(),
                new AuthorDto(response.author().id(),response.author().name(), response.author().avatar()),
                response.likes(),
                response.isLiked(),
                response.comments()
        );
    }

    public record AuthorDto(Long id, String name, String avatar) {}
}
