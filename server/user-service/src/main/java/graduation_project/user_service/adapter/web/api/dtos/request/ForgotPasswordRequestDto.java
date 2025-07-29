package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.application.usecases.request.ForgotPasswordRequest;
import jakarta.validation.constraints.Pattern;

public record ForgotPasswordRequestDto(
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@mgm-tp\\.com$",
                message = "Email must end with @mgm-tp.com"
        )
        String email
) {
        public ForgotPasswordRequest toRequest(){
                return ForgotPasswordRequest.builder().email(email).build();
        }
}