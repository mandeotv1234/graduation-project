package graduation_project.user_service.adapter.web.api.dtos.request;

public record CreateCommentDto(
        Long parent,
        String content
) {
}
