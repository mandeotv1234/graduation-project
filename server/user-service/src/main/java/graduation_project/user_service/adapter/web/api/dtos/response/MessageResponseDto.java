package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.MessageResponse;

public record MessageResponseDto(String message) {
    public static MessageResponseDto from(MessageResponse out){
        return new MessageResponseDto(out.message());
    }
}
