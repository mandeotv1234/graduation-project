package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.application.usecases.request.UserRequest;
import com.mgmtp.ihcmc.hive.server.domain.models.User;
import jakarta.validation.constraints.Pattern;

public record UserRequestDto(
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@mgm-tp\\.com$",
                message = "Email must end with @mgm-tp.com"
        )
        String email,
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
                message = "Password must be at least 8 characters and include 1 uppercase letter, 1 number, and 1 special character"
        )
        String password
) {
        public UserRequest toRequest(){
                return UserRequest.builder().email(email).password(password).build();
        }

        public User toModel() {
                return User.builder()
                        .email(email)
                        .password(password)
                        .build();
        }
}