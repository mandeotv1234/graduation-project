package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.domain.models.User;
import com.mgmtp.ihcmc.hive.server.domain.models.enums.Role;
import com.mgmtp.ihcmc.hive.server.domain.models.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoleStatusRequestDto {
    @NotNull
    private Role role;

    @NotNull
    private Status status;

    public User toModel() {
        return User.builder()
                .role(role)
                .status(status)
                .build();
    }
}
