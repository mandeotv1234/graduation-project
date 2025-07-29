package graduation_project.user_service.adapter.web.api.dtos.response;

import lombok.Builder;

@Builder
public record LoginResponseDto(
        String accessToken,
        boolean verified

) {
    public static LoginResponseDto fromVerifiedUser(String accessToken) {
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .verified(true)
                .build();
    }

    public static LoginResponseDto fromUnverifiedUser() {
        return LoginResponseDto.builder()
                .verified(false)
                .build();
    }
}