package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.application.usecases.request.UpdateUserSettingRequest;

public record UpdateUserSettingRequestDto(
    Object settingValue
) {
    public UpdateUserSettingRequest toRequest() {
        return new UpdateUserSettingRequest(
                settingValue
        );
    }
}