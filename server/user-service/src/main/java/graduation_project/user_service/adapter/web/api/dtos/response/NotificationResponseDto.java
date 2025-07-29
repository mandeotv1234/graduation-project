package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.NotificationResponse;
import com.mgmtp.ihcmc.hive.server.domain.models.notifications.Notification;

import java.time.LocalDateTime;
import java.util.List;

public record NotificationResponseDto (
        Long id,
        String message,
        Notification.ActionType actionType,
        String objectUrl,
        Boolean isRead,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){

    public static NotificationResponseDto fromModel(NotificationResponse notificationResponse) {
        return new NotificationResponseDto(
                notificationResponse.id(),
                notificationResponse.message(),
                notificationResponse.actionType(),
                notificationResponse.objectUrl(),
                notificationResponse.isRead(),
                notificationResponse.createdAt(),
                notificationResponse.updatedAt()
        );
    }

    public static List<NotificationResponseDto> fromModels(List<NotificationResponse> responses) {
        return responses.stream()
                .map(NotificationResponseDto::fromModel)
                .toList();
    }

}
