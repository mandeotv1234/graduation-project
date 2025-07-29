package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.RemoveBookmarkResponse;
import com.mgmtp.ihcmc.hive.server.application.usecases.response.UpdateEmailTemplateResponse;

public record UpdateEmailTemplateResponseDto(
    String message
) {
    public static UpdateEmailTemplateResponseDto from(UpdateEmailTemplateResponse response) {
        return new UpdateEmailTemplateResponseDto(response.message());
    }
}
