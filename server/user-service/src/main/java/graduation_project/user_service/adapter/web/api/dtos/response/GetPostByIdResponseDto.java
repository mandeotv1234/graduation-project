package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetPostByIdResponse;

import java.util.List;

public record GetPostByIdResponseDto(
        Long id,
        String title,
        String content,
        String postedAt,
        boolean isBookmarked,
        boolean isPublished,
        List<String> tags,
        boolean isLiked,
        int likes,
        AuthorDto author,
        String updatedAt
) {
    public static List<GetPostByIdResponseDto> fromResponses(List<GetPostByIdResponse> responses) {
        return responses.stream()
                .map(GetPostByIdResponseDto::fromResponse)
                .toList();
    }

    public static GetPostByIdResponseDto fromResponse(GetPostByIdResponse response) {
        return new GetPostByIdResponseDto(
                response.id(),
                response.title(),
                response.content(),
                response.postedAt().toString(),
                response.isBookmarked(),
                response.isPublished(),
                response.tags(),
                response.isLiked(),
                response.likes(),
                new AuthorDto(response.authorId(),response.authorName(), response.authorDepartment(), response.authorAvatar(), response.isAuthorFollowed()),
                response.updatedAt() != null ? response.updatedAt().toString() : null
        );
    }

    public record AuthorDto(Long id, String name, String department, String avatar, boolean isFollowed) {}
}
