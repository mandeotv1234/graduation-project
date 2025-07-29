package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.CommentResponse;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record CommentResponseDto (
        Long id,
        Author user,
        Long postId,
        Long parentId,
        String content,
        boolean deleted,
        Date createdAt,
        List<CommentResponseDto> replies
) {
    private record Author(Long id, String name, String avatar){
        public static Author from(CommentResponse.Author author){
            return new Author(author.id(), author.name(), author.avatar());
        }
    }

    public static CommentResponseDto from(CommentResponse response){
        List<CommentResponseDto> nestedReplies = response.replies() != null
                ? response.replies().stream()
                .map(CommentResponseDto::from)
                .toList()
                : List.of();
        return CommentResponseDto.builder()
                .id(response.id())
                .user(Author.from(response.user()))
                .postId(response.postId())
                .parentId(response.parentId())
                .content(response.content())
                .deleted(response.deleted())
                .createdAt(response.createdAt())
                .replies(nestedReplies)
                .build();
    }
}
