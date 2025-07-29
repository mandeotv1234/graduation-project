package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.RemoveBookmarkResponse;
import com.mgmtp.ihcmc.hive.server.application.usecases.response.RemoveLikeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveLikeResponseDto {

    private String message;

    public static RemoveLikeResponseDto from(RemoveLikeResponse response) {
        return new RemoveLikeResponseDto(response.message());
    }
}
