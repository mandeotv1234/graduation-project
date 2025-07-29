package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.CreatePostResponse;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        Long authorId,
        LocalDateTime time,
        List<String> tagTitles,
        Boolean isPublished
) {
    public static PostResponseDto from(CreatePostResponse response) {
        CreatePostResponse.Post post = response.post();
        return new PostResponseDto(
                post.id(),
                post.title(),
                post.content(),
                post.authorId(),
                post.time(),
                post.tagTitles(),
                post.isPublished()
        );
    }
}
