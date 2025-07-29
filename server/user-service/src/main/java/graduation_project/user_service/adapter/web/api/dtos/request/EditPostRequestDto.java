package graduation_project.user_service.adapter.web.api.dtos.request;


import com.mgmtp.ihcmc.hive.server.application.usecases.request.EditPostRequest;

import java.util.List;

public record EditPostRequestDto (String title, String content, List<String> tagTitles, boolean isPublished) {
    public EditPostRequest toRequest() {
        return new EditPostRequest(title, content, tagTitles, isPublished);
    }
}
