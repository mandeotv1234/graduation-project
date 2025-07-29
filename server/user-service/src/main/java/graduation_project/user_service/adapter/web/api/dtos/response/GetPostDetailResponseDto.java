package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetPostDetailResponse;
import java.util.List;

public record GetPostDetailResponseDto(
        Long id,
        String title,
        String content,
        String postedAt,
        boolean isBookmarked,
        List<String> tags,
        AuthorDto author,
        boolean isPublished,
        String updatedAt
) {
    public static GetPostDetailResponseDto fromResponse(GetPostDetailResponse response) {
        return new GetPostDetailResponseDto(
                response.id(),
                response.title(),
                response.content(),
                response.postedAt().toString(),
                response.isBookmarked(),
                response.tags(),
                new AuthorDto(response.authorId(),response.authorName(), response.authorDepartment(), response.authorAvatar()),
                response.isPublished(),
                response.updatedAt() != null ? response.updatedAt().toString() : null
        );
    }
    public record AuthorDto(Long id, String name, String department, String avatar) {}
}
