package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.RemoveBookmarkResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveBookmarkResponseDto {

    private String message;

    public static RemoveBookmarkResponseDto from(RemoveBookmarkResponse response) {
        return new RemoveBookmarkResponseDto(response.message());
    }
}
