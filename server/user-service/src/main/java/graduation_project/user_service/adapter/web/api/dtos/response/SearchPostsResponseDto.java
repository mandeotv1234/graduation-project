
package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetPostsResponse;
import com.mgmtp.ihcmc.hive.server.application.usecases.response.SearchPostsResponse;

import java.util.List;

public record SearchPostsResponseDto(
        Long id,
        String title,
        String content,
        String postedAt,
        boolean isBookmarked,
        List<String> tags,
        int likes,
        boolean isLiked,
        AuthorDto author,
        int comments
) {
    public static List<SearchPostsResponseDto> fromResponses(List<SearchPostsResponse> responses) {
        return responses.stream()
                .map(SearchPostsResponseDto::fromResponse)
                .toList();
    }

    public static SearchPostsResponseDto fromResponse(SearchPostsResponse response) {
        return new SearchPostsResponseDto(
                response.id(),
                response.title(),
                response.content(),
                response.postedAt().toString(),
                response.isBookmarked(),
                response.tags(),
                response.likes(),
                response.isLiked(),
                new AuthorDto(response.authorId(),response.authorName(), response.authorAvatar()),
                response.comments()
        );
    }

    public record AuthorDto(Long id, String name, String avatar) {}
}