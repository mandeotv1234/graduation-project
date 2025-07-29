package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetUserResponseWithFollowedFlag;
import com.mgmtp.ihcmc.hive.server.domain.models.enums.Role;
import com.mgmtp.ihcmc.hive.server.domain.models.enums.Status;

import java.util.List;

public record GetUsersWithFollowedFlagResponseDto(
        Long id,
        String email,
        Status status,
        Role role,
        String fullName,
        String imageUrl,
        String department,
        String bio,
        Boolean isFollowed,
        String about
) {

    public static GetUsersWithFollowedFlagResponseDto fromResponse(final GetUserResponseWithFollowedFlag getUsersResponse) {
        return new GetUsersWithFollowedFlagResponseDto(
                getUsersResponse.id(),
                getUsersResponse.email(),
                getUsersResponse.status(),
                getUsersResponse.role(),
                getUsersResponse.fullName(),
                getUsersResponse.imageUrl(),
                getUsersResponse.department(),
                getUsersResponse.bio(),
                getUsersResponse.isFollowed(),
                getUsersResponse.about());
    }

    public static List<GetUsersWithFollowedFlagResponseDto> fromResponses(List<GetUserResponseWithFollowedFlag> userResponses) {
        return userResponses.stream().map(GetUsersWithFollowedFlagResponseDto::fromResponse).toList();
    }
}

