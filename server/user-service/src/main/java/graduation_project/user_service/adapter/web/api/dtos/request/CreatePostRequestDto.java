package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.application.usecases.request.CreatePostRequest;

import java.util.List;

public record CreatePostRequestDto(
        String title,
        String content,
        Long authorId,
        List<String> tagTitles,
        Boolean isPublished
) {

    public CreatePostRequest toRequest() {
        CreatePostRequest request = new CreatePostRequest(this.title, this.content, this.authorId, this.tagTitles, this.isPublished);
        return request;
    }


}
