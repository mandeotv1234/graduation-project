package graduation_project.user_service.adapter.web.api.dtos.response;


import com.mgmtp.ihcmc.hive.server.application.usecases.response.UpdateUserSettingResponse;

public record UpdateUserSettingResponseDto(
        Long id,
        Long userId,
        String settingKey,
        Object settingValue,
        String createdAt,
        String updatedAt
) {
    public static UpdateUserSettingResponseDto fromResponse(UpdateUserSettingResponse response) {
        return new UpdateUserSettingResponseDto(
                response.id(),
                response.userId(),
                response.settingKey(),
                response.settingValue(),
                response.createdAt().toString(),
                response.updatedAt().toString()
        );
    }

}