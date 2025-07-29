package graduation_project.user_service.adapter.web.api.dtos.response;

import com.mgmtp.ihcmc.hive.server.application.usecases.response.GetUsersResponse;
import com.mgmtp.ihcmc.hive.server.domain.models.enums.Role;
import com.mgmtp.ihcmc.hive.server.domain.models.enums.Status;
import java.util.List;

public record GetUsersResponseDto(
        Long id,
        String email,
        Status status,
        Role role,
        String fullName,
        String imageUrl,
        String department,
        String bio,
        String about
) {

    public static GetUsersResponseDto fromResponse(final GetUsersResponse getUsersResponse) {
        return new GetUsersResponseDto(
                getUsersResponse.id(),
                getUsersResponse.email(),
                getUsersResponse.status(),
                getUsersResponse.role(),
                getUsersResponse.fullName(),
                getUsersResponse.imageUrl(),
                getUsersResponse.department(),
                getUsersResponse.bio(),
                getUsersResponse.about());
    }

    public static List<GetUsersResponseDto> fromResponses(List<GetUsersResponse> userResponses) {
        return userResponses.stream().map(GetUsersResponseDto::fromResponse).toList();
    }
}
