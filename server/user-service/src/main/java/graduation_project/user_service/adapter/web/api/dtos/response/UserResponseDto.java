package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.UserResponse;
import com.mgmtp.ihcmc.hive.server.domain.models.User;
import com.mgmtp.ihcmc.hive.server.domain.models.enums.Role;
import com.mgmtp.ihcmc.hive.server.domain.models.enums.Status;
import lombok.Builder;

@Builder
public record UserResponseDto(
        Long id,
        String email,
        Status status, Role role,
        String fullName,
        String imageUrl,
        String department,
        String bio,
        String about ) {
    public static UserResponseDto from(UserResponse out){
        return UserResponseDto.builder()
                .id(out.id())
                .email(out.email()).status(out.status())
                .role(out.role()).fullName(out.fullName())
                .imageUrl(out.imageUrl())
                .department(out.department())
                .bio(out.bio())
                .about(out.about())
                .build();
    }

    public static UserResponseDto fromModel(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail()).status(user.getStatus())
                .role(user.getRole()).fullName(user.getFullName())
                .imageUrl(user.getImageUrl())
                .department(user.getDepartment())
                .bio(user.getBio())
                .about(user.getAbout())
                .build();
    }
}
