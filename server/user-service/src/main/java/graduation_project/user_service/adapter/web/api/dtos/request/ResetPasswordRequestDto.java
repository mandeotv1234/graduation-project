package graduation_project.user_service.adapter.web.api.dtos.request;

import com.mgmtp.ihcmc.hive.server.application.usecases.request.ResetPasswordRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.AssertTrue;

public record ResetPasswordRequestDto(
        @NotBlank(message = "Token must not be blank")
        String token,

        @NotBlank(message = "New password must not be blank")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
                message = "Password must be at least 8 characters long and include uppercase, lowercase, number, and special character"
        )
        String newPassword,

        @NotBlank(message = "Confirm password must not be blank")
        String confirmPassword
) {
    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordsMatching() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
    public ResetPasswordRequest toRequest(){
        return ResetPasswordRequest.builder().token(token).newPassword(newPassword).build();
    }
}
