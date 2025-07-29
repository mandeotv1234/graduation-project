package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.application.usecases.request.MarkNotificationAsReadRequest;
import lombok.Data;

public record MarkNotificationAsReadRequestDto(
    Long notificationId,
    String objectUrl,
    String type
) {

    public MarkNotificationAsReadRequest toRequest() {
        return new MarkNotificationAsReadRequest(notificationId, objectUrl, type);
    }
}