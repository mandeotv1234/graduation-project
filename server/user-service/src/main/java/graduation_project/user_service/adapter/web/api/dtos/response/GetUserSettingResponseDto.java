package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetUserSettingResponse;

public record GetUserSettingResponseDto(
        Long id,
        Long userId,
        String settingKey,
        Object settingValue,
        String createdAt,
        String updatedAt
) {
    public static GetUserSettingResponseDto fromResponse(GetUserSettingResponse getUserSettingResponse) {
        return new GetUserSettingResponseDto(
                getUserSettingResponse.id(),
                getUserSettingResponse.userId(),
                getUserSettingResponse.settingKey(),
                getUserSettingResponse.settingValue(),
                getUserSettingResponse.createdAt().toString(),
                getUserSettingResponse.updatedAt().toString()
        );
    }
}