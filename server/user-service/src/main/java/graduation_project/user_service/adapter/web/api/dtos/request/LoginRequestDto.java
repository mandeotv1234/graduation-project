package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.domain.models.User;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginRequestDto {
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@mgm-tp\\.com$",
            message = "Email must end with @mgm-tp.com"
    )
    private final String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
            message = "Password must be at least 8 characters and include 1 uppercase letter, 1 number, and 1 special character"
    )
    private final String password;

    public User toModel() {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}