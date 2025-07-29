
package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetPostsResponse;

import java.util.List;

public record GetPostsResponseDto(
        Long id,
        String title,
        String content,
        String postedAt,
        boolean isBookmarked,
        List<String> tags,
        boolean isLiked,
        int likes,
        AuthorDto author,
        int comments
) {
    public static List<GetPostsResponseDto> fromResponses(List<GetPostsResponse> responses) {
        return responses.stream()
                .map(GetPostsResponseDto::fromResponse)
                .toList();
    }

    public static GetPostsResponseDto fromResponse(GetPostsResponse response) {
        return new GetPostsResponseDto(
                response.id(),
                response.title(),
                response.content(),
                response.postedAt().toString(),
                response.isBookmarked(),
                response.tags(),
                response.isLiked(),
                response.likes(),
                new AuthorDto(response.authorId(),response.authorName(), response.authorAvatar()),
                response.comments()
        );
    }

    public record AuthorDto(Long id, String name, String avatar) {}
}