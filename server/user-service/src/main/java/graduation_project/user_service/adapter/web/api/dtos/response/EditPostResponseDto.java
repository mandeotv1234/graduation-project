package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.EditPostResponse;

import java.time.LocalDateTime;
import java.util.List;

public record EditPostResponseDto(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        List<String> tagTitles,
        Boolean isPublished,
        LocalDateTime updatedAt
) {
    public static EditPostResponseDto from(EditPostResponse response) {
        return new EditPostResponseDto(
                response.id(),
                response.title(),
                response.content(),
                response.createdAt(),
                response.tags(),
                response.isPublished(),
                response.updatedAt()
        );
    }
}
