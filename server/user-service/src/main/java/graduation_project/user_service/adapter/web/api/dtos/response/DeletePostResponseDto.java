package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.DeletePostResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeletePostResponseDto {
    private String message;

    public static DeletePostResponseDto from(DeletePostResponse response) {
        return new DeletePostResponseDto(response.message());
    }
}
